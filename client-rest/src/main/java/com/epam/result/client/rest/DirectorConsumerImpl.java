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

    @Value("${protocol}://${host}:${port}${prefix}")
    private String url;

    @Value("${point.director}")
    private String urlDirector;

    @Value("${point.directors}")
    private String urlDirectors;

    ClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    RestTemplate restTemplate = new RestTemplate(requestFactory);

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public List<DirectorDTO> getAllDirectorDTO() {
        ResponseEntity responseEntity = restTemplate.getForEntity(url+urlDirectors, Object.class);
        List<DirectorDTO> directorsDTO = (List<DirectorDTO>)responseEntity.getBody();
        return directorsDTO;
    }

    @Override
    public int addDirector(Director director) {
            return restTemplate.postForObject(url + urlDirector, director, Integer.class);
    }

    @Override
    public Director getDirectorById(Integer directorId) {
        ResponseEntity<Director> responseEntity = restTemplate
                .getForEntity(url + urlDirector+"/{directorId}", Director.class, directorId);
        return responseEntity.getBody();
    }

    @Override
    public void updateDirector(Director director) {
        restTemplate.put(url+urlDirector, director);
    }

    @Override
    public void deleteDirector(Integer directorId) {
        restTemplate.delete(url+urlDirector+"/{directorId}", directorId);
    }
}
