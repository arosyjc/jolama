package com.jolama;

import com.github.tjake.jlama.model.AbstractModel;
import com.github.tjake.jlama.model.ModelSupport;
import com.github.tjake.jlama.model.functions.Generator;
import com.github.tjake.jlama.safetensors.DType;
import com.github.tjake.jlama.safetensors.prompt.PromptContext;
import com.github.tjake.jlama.util.Downloader;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Hello world!
 *
 */
public class App{
    public void sample() throws IOException {
        String model = "tjake/Llama-3.2-1B-Instruct-JQ4";
        String workingDirectory = "./models";

        String prompt = "What is the best season to plant avocados?";

        // Downloads the model or just returns the local path if it's already downloaded
        File localModelPath = new Downloader(workingDirectory, model).huggingFaceModel();

        // Loads the quantized model and specified use of quantized memory
        AbstractModel m = ModelSupport.loadModel(localModelPath, DType.F32, DType.I8);

        PromptContext ctx;
        // Checks if the model supports chat prompting and adds prompt in the expected format for this model
        if (m.promptSupport().isPresent()) {
            ctx = m.promptSupport()
                    .get()
                    .builder()
                    .addSystemMessage("You are a helpful chatbot who writes short responses.")
                    .addUserMessage(prompt)
                    .build();
        } else {
            ctx = PromptContext.of(prompt);
        }

        System.out.println("Prompt: " + ctx.getPrompt() + "\n");
        // Generates a response to the prompt and prints it
        // The api allows for streaming or non-streaming responses
        // The response is generated with a temperature of 0.7 and a max token length of 256
        Generator.Response r = m.generateBuilder()
                .session(UUID.randomUUID()) //By default, UUID.randomUUID()
                .promptContext(ctx) // Required or use prompt(String text)
                .ntokens(256) //By default, 256
                .temperature(0.0f) //By default, 0.0f
                .onTokenWithTimings((s, aFloat) -> {}) //By default, (s, aFloat) -> {}, nothing
                .generate();

        System.out.println(r.responseText);
    }
    /**
     * invoke by api method
     * */
    public static void sampleApi() throws IOException {
        String model = "tjake/Llama-3.2-1B-Instruct-JQ4";
        String workingDirectory = "./models";

        String prompt = "What is the best season to plant avocados?";

        // Downloads the model or just returns the local path if it's already downloaded
        File localModelPath = new Downloader(workingDirectory, model).huggingFaceModel();

        // Loads the quantized model and specified use of quantized memory
        AbstractModel m = ModelSupport.loadModel(localModelPath, DType.F32, DType.I8);

        PromptContext ctx;
        // Checks if the model supports chat prompting and adds prompt in the expected format for this model
        if (m.promptSupport().isPresent()) {
            ctx = m.promptSupport()
                    .get()
                    .builder()
                    .addSystemMessage("You are a helpful chatbot who writes short responses.")
                    .addUserMessage(prompt)
                    .build();
        } else {
            ctx = PromptContext.of(prompt);
        }

        System.out.println("Prompt: " + ctx.getPrompt() + "\n");
        // Generates a response to the prompt and prints it
        // The api allows for streaming or non-streaming responses
        // The response is generated with a temperature of 0.7 and a max token length of 256
        Generator.Response r = m.generateBuilder()
                .session(UUID.randomUUID()) //By default, UUID.randomUUID()
                .promptContext(ctx) // Required or use prompt(String text)
                .ntokens(256) //By default, 256
                .temperature(0.0f) //By default, 0.0f
                .onTokenWithTimings((s, aFloat) -> {}) //By default, (s, aFloat) -> {}, nothing
                .generate();

        System.out.println(r.responseText);
    }

    public static void main(String[] args) {
        try {
            sampleApi();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
