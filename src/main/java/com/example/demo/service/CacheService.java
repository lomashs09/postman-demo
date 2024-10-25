package com.example.demo.service;


import com.example.demo.util.ErrorMessage;
import com.example.demo.util.vo.CacheRequest;
import com.example.demo.util.vo.CacheValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CacheService {
    private static final int SIZE_LIMIT = 4;
    Map<String, CacheValue> cache = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(CacheService.class);

    public String getCacheValue(String key) {
        logger.info("Getting all cache");
        checkCacheExist(key);
        return cache.get(key).getValue();
    }

    public void addOrUpdateCache(CacheRequest request) {
        logger.info("Adding cache");
        CacheValue value = getCacheValue(request);
        cache.put(request.getKey(), value);
        printCache();
    }

    public void deleteCache(CacheRequest request) {
        logger.info("Deleting cache");
        checkCacheExist(request.getKey());
        cache.remove(request.getKey());
    }

    private void checkCacheExist(String key) {
        if (!cache.containsKey(key)) {
            throw new RuntimeException(ErrorMessage.CACHE_NOT_FOUND);
        }
    }


    private  CacheValue getCacheValue(CacheRequest request) {
        CacheValue value = null;
        if (cache.containsKey(request.getKey())) {
            value = cache.get(request.getKey());
            value.setFrequency(value.getFrequency() +1);
        }
        else {
            if (cache.size() == SIZE_LIMIT) {
                String key = findLFUKey();
                cache.remove(key);
                return new CacheValue(request.getValue(), 1);
            }
            value = new CacheValue(request.getValue(), 1);
        }
        return  value;
    }

    private String findLFUKey() {
        String result = null;
//        Collections.sort(people, (a, b) -> a.name.compareToIgnoreCase(b.name));
        Map<String, CacheValue> sortedCache = cache.entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue().getFrequency(), entry1.getValue().getFrequency()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, // if duplicate keys exist, keep the first one
                        LinkedHashMap::new // maintains insertion order
                ));
        for (String key: sortedCache.keySet()) {
            result = key;
        }
        return result;
    }


    private void printCache() {
        for(String key: cache.keySet()) {
            logger.info("key is {}, value is {}, frequency is {}", key, cache.get(key).getValue(), cache.get(key).getFrequency());
        }
    }


}
