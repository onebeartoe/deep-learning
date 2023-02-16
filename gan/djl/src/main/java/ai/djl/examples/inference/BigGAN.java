
package ai.djl.examples.inference;

/*
 * Copyright 2021 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */


import ai.djl.Application;
import ai.djl.ModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.ndarray.types.Shape;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;
import ai.djl.util.PairList;
import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** An example of generation using BigGAN. 
 *  This class was originally from this github.com account:
 * 
 *          https://github.com/deepjavalibrary/djl/blob/master/examples/src/main/java/ai/djl/examples/inference/BigGAN.java
 * 
 *  That project has a bunch of dependencies that are not needed for this Java file, so I just made a copy here.
 */
public final class BigGAN 
{
    private List<String> names = null;
    
    private static final Logger logger = LoggerFactory.getLogger(BigGAN.class);

    private void describePairList(PairList<String, Shape> input)
    {
        if(input == null)
        {
            System.err.println("The descriptionInput is null.");
        }
        else
        {
            input.stream()
                .forEach(i -> 
                {
                    String s = i.getKey() + " - " + i.getValue();

                    System.out.println("s = " + s);
                });                       
        }
    }
    
    private void describeInput(ZooModel<int[], Image[]> model) 
    {
            PairList<String, Shape> describeInput = model.describeInput();
            
            if(describeInput == null)
            {
                System.err.println("The descriptionInput is null.");
            }
            else
            {
                describeInput.stream()
                    .forEach(i -> 
                    {
                        String s = i.getKey() + " - " + i.getValue();
                        
                        System.out.println("s = " + s);
                    });                       
            } 
    }

    private void artifactNames(ZooModel<int[], Image[]> model) 
    {
        String[] artifactNames = model.getArtifactNames();
        
        System.out.println("artifactNames:");
        
        Stream.of(artifactNames)
                .forEach(n -> 
                {
                    System.out.println("n = " + n);
                });
    }

    public static void main(String[] args) throws ModelException, TranslateException, IOException 
    {
        BigGAN bigGan = new BigGAN();
        
        Image[] generatedImages = bigGan.generate();
        logger.info("Using PyTorch Engine. {} images generated.", generatedImages.length);
        bigGan.saveImages(generatedImages);
    }

    private void saveImages(Image[] generatedImages) throws IOException 
    {
        Path outputPath = Paths.get("build/output/gan/");
        Files.createDirectories(outputPath);

        for (int i = 0; i < generatedImages.length; ++i) {
            Path imagePath = outputPath.resolve("image" + i + ".png");
            generatedImages[i].save(Files.newOutputStream(imagePath), "png");
        }
        
        logger.info("Generated images have been saved in: {}", outputPath);
    }

    public Image[] generate() throws IOException, ModelException, TranslateException 
    {
        Criteria<int[], Image[]> criteria =
                Criteria.builder()
                        .optApplication(Application.CV.IMAGE_GENERATION)
                        .setTypes(int[].class, Image[].class)
                        .optFilter("size", "256")
                        .optArgument("truncation", 0.4f)
                        .optEngine("PyTorch")
                        .optProgress(new ProgressBar())
                        .build();

//        int[] input = {100, 207, 971, 970, 933};
        int[] input = {100, 101, 102, 103, 104};

        try (ZooModel<int[], Image[]> model = criteria.loadModel();
                Predictor<int[], Image[]> generator = model.newPredictor()) 
        {
            System.out.println("called");
            
            describeInput(model);
            
//what is next            ????
            String toString = model.toString();
            System.out.println("toString = " + toString);
            
            PairList<String, Shape> describeOutput = model.describeOutput();
            System.out.println("describeOutput:");
            describePairList(describeOutput);

            artifactNames(model);

            Map<String, Object> arguments = criteria.getArguments();
            
            Set<String> keySet = arguments.keySet();
            
            keySet.forEach(key -> 
            {
                Object value = arguments.get(key);
                
                System.out.println(key + " = " + value);                               
            }); 
            
            return generator.predict(input);
        }
    }

    public List<String> categoryNames() throws URISyntaxException, IOException 
    {
        URL url = getClass().getResource("/synset_imagenet.txt");
        URI uri = url.toURI();
        File infile = new File(uri);
        Path inpath = infile.toPath();

        if(names == null)
        {
            // read the mapping file from the classpath
            try(Stream<String> nameStream = Files.lines(inpath) )
            {
                names = nameStream.collect(Collectors.toList() ) ;
            };            
        }
        
        return names;
    }
}
