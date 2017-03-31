package com.epam.result.client.rest;

import com.epam.result.client.rest_api.DirectorConsumer;
import com.epam.result.dao.Director;
import com.epam.result.dao.DirectorDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author  Bogdan Viktor
 */
public class DirectorConsumerImpl implements DirectorConsumer {

    @Value("${protocol}://${host}:${port}/${prefix}")
    private String url;

    @Value("${point.director}")
    private String urlDirector;

    @Value("${point.directors}")
    private String urlDirectors;

    ClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    RestTemplate restTemplate = new RestTemplate(requestFactory);
    {
        restTemplate.setErrorHandler(new CustomResponseErrorHandler());
    }


    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Director> getAllDirectors() {
        return null;
    }

    //curl -v localhost:8080/rest-1.0-SNAPSHOT/directors
    @Override
    public List<DirectorDTO> getAllDirectorDTO() {
        System.out.println(urlDirector);
        ResponseEntity responseEntity = restTemplate.getForEntity(url+urlDirectors, Object.class);
        List<DirectorDTO> directorsDTO = (List<DirectorDTO>)responseEntity.getBody();
        return directorsDTO;
    }

    @Override
    public int addDirector(Director director) {
        return 0;
    }

    @Override
    public Director getDirectorById(Integer id) {
        return null;
    }

    @Override
    public void updateDirector(Director director) {

    }

    @Override
    public void deleteDirector(Integer directorID) {

    }
}
