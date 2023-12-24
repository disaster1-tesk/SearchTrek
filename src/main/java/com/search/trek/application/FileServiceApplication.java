package com.search.trek.application;

import cn.hutool.json.JSONUtil;
import com.search.trek.domain.Result;
import com.search.trek.domain.Results;
import com.search.trek.infrastructure.client.ai.baidu.BaiduOpenApiClient;
import com.search.trek.infrastructure.client.ai.minimax.MiniMaxApiClient;
import com.search.trek.infrastructure.client.ai.minimax.entity.embedding.EmbeddingReq;
import com.search.trek.infrastructure.client.ai.minimax.entity.embedding.EmbeddingRes;
import com.search.trek.infrastructure.client.ai.minimax.enums.Model;
import com.search.trek.infrastructure.client.ai.minimax.enums.Type;
import com.search.trek.infrastructure.client.vector.pinecone.PineConeApiClient;
import com.search.trek.infrastructure.client.vector.pinecone.entity.vector.update.UpdateReq;
import com.search.trek.infrastructure.client.vector.pinecone.entity.vector.upset.UpSertReq;
import com.search.trek.infrastructure.utils.TextSplitterUtil;
import com.search.trek.infrastructure.utils.VerificationCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.assertj.core.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class FileServiceApplication {


    private final MiniMaxApiClient miniMaxApiClient;

    private final PineConeApiClient pineConeApiClient;

    private final BaiduOpenApiClient baiduOpenApiClient;

    @Autowired
    public FileServiceApplication(MiniMaxApiClient miniMaxApiClient, PineConeApiClient pineConeApiClient, BaiduOpenApiClient baiduOpenApiClient) {
        this.miniMaxApiClient = miniMaxApiClient;
        this.pineConeApiClient = pineConeApiClient;
        this.baiduOpenApiClient = baiduOpenApiClient;
    }


    public Result upload(MultipartFile multipartFile) {
        PDDocument document;
        String doc = "";
        String originalFilename = "";

        File file;
        try {
            originalFilename = multipartFile.getOriginalFilename();
            String[] filename = originalFilename.split("\\.");

            file = File.createTempFile(VerificationCodeGenerator.generateCode(6), "." + filename[filename.length - 1]);
            multipartFile.transferTo(file);

            document = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            doc = pdfStripper.getText(document);

            file.deleteOnExit();
            document.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }

        TextSplitterUtil textSplitter = new TextSplitterUtil(null, 1000, 200);
        List<String> content = textSplitter.splitText(doc);
        if (content.size() >= 320) {
            return null;
        }

        //TODO 加facade层调用所有模型
//        HashMap<String, String> params = new HashMap<>();
//        params.put("grant_type", "client_credentials");
//        params.put("client_id", "1DGkrPe8GiqO7clikbMEsCkd");
//        params.put("client_secret", "R4XeFlFZtgGSpUGLX5NHajw7lXWOCCDU");
//
//
//        TokenRes token = baiduOpenApiClient.token(params);
//        String access_token = token.getAccess_token();
//        com.search.trek.infrastructure.client.ai.baidu.entity.embedding.v1.EmbeddingRes embeddings1 = baiduOpenApiClient.embeddings(access_token, com.search.trek.infrastructure.client.ai.baidu.entity.embedding.v1.EmbeddingReq.builder()
//                .input(content)
//                .build());
//        embeddings1.getData().get(0).getEmbedding();
        EmbeddingRes embeddings = miniMaxApiClient.embeddings(EmbeddingReq.builder()
                .model(Model.EM_BO_01.getName())
                .type(Type.DB.getName())
                .texts(content)
                .build());

        List<List<Float>> vectors = embeddings.getVectors();
        List<UpdateReq> updateReqs = new ArrayList<>();
        String id = "ctx";

        for (int i = 0; i < vectors.size(); i++) {
            List<Float> floats = vectors.get(i);
            updateReqs.add(UpdateReq.builder()
                    .values(floats)
                    .id(id + i)
                    .metadata(Maps.newHashMap("content", content.get(i)))
                    .build());
        }

        Map result = pineConeApiClient.upsert(UpSertReq.builder()
                .namespace("hello_bike")
                .vectors(updateReqs)
                .build());
        log.info("result= {}", JSONUtil.toJsonStr(result));
        return Results.ok();
    }
}
