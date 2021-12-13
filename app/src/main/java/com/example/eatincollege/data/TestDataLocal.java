package com.example.eatincollege.data;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;

public class TestDataLocal implements TestDataInterface {
    public static TestDataLocal INSTANCE = null;

    public static TestDataLocal getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TestDataLocal();
        }
        return INSTANCE;
    }

    TestDataLocal() {}

    @Override
    public Observable<Api_Test> get_Api_Test(int index) {
        return new Observable<Api_Test>() {
            @Override
            protected void subscribeActual(Observer<? super Api_Test> observer) {
                Api_Test api_test = new Api_Test();
                api_test.name = "id_" + index;
                observer.onNext(api_test);
            }
        };
    }

    //mData.get_Api_FoodBaseInfo(f_id).subscribe(new Observer<Api_FoodBaseInfo>() {};
}
