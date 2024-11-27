package com.cn.test;

import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.protobuf.ByteString;
import com.google.cloud.speech.v1.RecognitionAudio;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/***            : TODO
 * @Copyright All rights Reserved, Designed By Circle Harmony Medical Insurance
 * @Project     ：SpringCloud
 * @Title       ：SpeechToTextDemo
 * @Description ：TODO
 * @Version     ：Ver1.0
 * @Author      ：lz
 * @Date        ：2024-10-17 10:12
 * @Dept        ：Information Technology Department
 * @Company     ：Circle Harmony Medical Insurance
 ***/
public class SpeechToTextDemo {

    public static void main(String... args) throws Exception {
        // 设置Google Cloud Speech API的认证信息
        String encoding = "LINEAR16";
        int sampleRateHertz = 16000;
        String languageCode = "en-US";

        // 创建SpeechClient实例
        try (SpeechClient speechClient = SpeechClient.create()) {
            // 从文件中读取音频数据
            Path path = Paths.get("path/to/audio/file.wav");
            byte[] data = Files.readAllBytes(path);
            ByteString audioBytes = ByteString.copyFrom(data);

            // 设置识别配置
            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(AudioEncoding.LINEAR16)
                    .setSampleRateHertz(sampleRateHertz)
                    .setLanguageCode(languageCode)
                    .build();

            // 创建识别请求
//            RecognizeResponse response = speechClient.recognize(
//                    RecognitionConfig.newBuilder().setConfig(config).build(),
//                    RecognitionAudio.newBuilder().setAudio(audioBytes).build()
//            );

            // 输出识别结果
//            for (SpeechRecognitionResult result : response.getResultsList()) {
//                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
//                System.out.printf("Transcription: %s%n", alternative.getTranscript());
//            }
        }
    }

}
