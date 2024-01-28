package com.eatoreo.filmbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class MediaController {

    private final ResourceLoader resourceLoader;
    public MediaController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/api/filmcard/{name}")
    public ModelAndView getMethodName(@PathVariable String name) {
        var m = new ModelAndView("api/filmcard");

        
        //TODO: move this out of controller vvv
        try {
            var jsonFile = resourceLoader.getResource("classpath:films.json").getFile();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonFile);
            var filmNode = rootNode.get(name);
            List<JsonNode> posterList = new ArrayList<JsonNode>();
            filmNode.get("posters").elements().forEachRemaining(posterList::add);
            Random rand = new Random();
            var posterNode = posterList.get(rand.nextInt(posterList.size()));
            
            m.addObject("name", name);
            m.addObject("title", filmNode.get("title").asText());
            m.addObject("poster", posterNode.get("filename").asText());
            m.addObject("preview", posterNode.get("preview").asText());
            m.addObject("titleclasses", posterNode.get("titleclasses").asText());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return m;
    }

    @GetMapping(value = "/media/dbh")
    @ResponseBody
    public ResponseEntity<StreamingResponseBody> playMediaV01(@RequestHeader(value = "Range", required = false) String rangeHeader)
    {
        try
        {
            StreamingResponseBody responseStream;
            String filePathString = "/Users/lukas/Documents/CodeProjects/Filmbox/filmbox/src/main/resources/static/films/dbh/film.webm";
            Path filePath = Paths.get(filePathString);
            Long fileSize = Files.size(filePath);
            byte[] buffer = new byte[1024];      
            final HttpHeaders responseHeaders = new HttpHeaders();

            if (rangeHeader == null)
            {
                responseHeaders.add("Content-Type", "video/webm");
                responseHeaders.add("Content-Length", fileSize.toString());
                responseStream = os -> {
                    RandomAccessFile file = new RandomAccessFile(filePathString, "r");
                    try (file)
                    {
                    long pos = 0;
                    file.seek(pos);
                    while (pos < fileSize - 1)
                    {                            
                        file.read(buffer);
                        os.write(buffer);
                        pos += buffer.length;
                    }
                    os.flush();
                    } catch (Exception e) {}
                };
                
                return new ResponseEntity<StreamingResponseBody>
                        (responseStream, responseHeaders, HttpStatus.OK);
            }

            String[] ranges = rangeHeader.split("-");
            Long rangeStart = Long.parseLong(ranges[0].substring(6));
            Long rangeEnd;
            if (ranges.length > 1)
            {
                rangeEnd = Long.parseLong(ranges[1]);
            }
            else
            {
                rangeEnd = fileSize - 1;
            }
                
            if (fileSize < rangeEnd)
            {
                rangeEnd = fileSize - 1;
            }

            String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
            responseHeaders.add("Content-Type", "video/webm");
            responseHeaders.add("Content-Length", contentLength);
            responseHeaders.add("Accept-Ranges", "bytes");
            responseHeaders.add("Content-Range", "bytes" + " " + 
                                rangeStart + "-" + rangeEnd + "/" + fileSize);
            final Long _rangeEnd = rangeEnd;
            responseStream = os -> {
                RandomAccessFile file = new RandomAccessFile(filePathString, "r");
                try (file)
                {
                    long pos = rangeStart;
                    file.seek(pos);
                    while (pos < _rangeEnd)
                    {                        
                    file.read(buffer);
                    os.write(buffer);
                    pos += buffer.length;
                    }
                    os.flush();
                }
                catch (Exception e) {}
            };
                
            return new ResponseEntity<StreamingResponseBody>
                    (responseStream, responseHeaders, HttpStatus.PARTIAL_CONTENT);
        }
        catch (FileNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (IOException e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
