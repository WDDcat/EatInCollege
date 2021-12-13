package com.example.eatincollege.data;

import io.reactivex.Observable;

public interface TestDataInterface {
    Observable<Api_Test> get_Api_Test(int index);
}
