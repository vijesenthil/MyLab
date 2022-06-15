package com.gt.user.response;

import com.gt.user.model.User;

import java.util.List;

public class ResponseResult {
    private List<User> results;

    public ResponseResult(List<User> results) {
        this.results = results;
    }

    public List<User> getResults() {
        return results;
    }
}
