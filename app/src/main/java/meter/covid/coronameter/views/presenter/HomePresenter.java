package meter.covid.coronameter.views.presenter;

import android.content.Context;

import org.json.JSONObject;

import javax.inject.Inject;

import meter.covid.coronameter.apiClient.ApiInterface;
import meter.covid.coronameter.baseClass.BasePresenter;
import meter.covid.coronameter.dagger.DaggerApplication;
import meter.covid.coronameter.helper.Utilities;
import meter.covid.coronameter.models.LatestResponse;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by belal on 3/20/20.
 */

public class HomePresenter implements BasePresenter<HomeView> {

    HomeView mView;
    boolean isLoaded = false;
    @Inject
    ApiInterface mApiInterface;
    @Inject
    Context mContext;



    @Override
    public void onAttach(HomeView view) {
        mView = view;
        mView.onAttache();

    }



    @Override
    public void onDetach() {
        mView = null;
    }
    //create Constructor to get reference of api interface object
    public HomePresenter(Context context){
        ((DaggerApplication)context).getAppComponent().inject(this);
    }

    //this function created to load items from specific endpoint
    public void loginPresenter(String email , String password) {


            if (!Utilities.checkConnection(mContext)) {
                mView.showErrorInternetConnection();
                checkConnection(false);
                return;
            } else {
                mView.showLoading();

                mApiInterface.getLatestCovid()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<LatestResponse>() {
                            @Override
                            public final void onCompleted() {

                                mView.hideLoading();
                            }

                            @Override
                            public final void onError(Throwable e) {


                                mView.hideLoading();

                            }

                            @Override
                            public final void onNext(LatestResponse response) {
                                isLoaded = true;


                            }
                        });


            }



    }








    void checkConnection(boolean isConnected) {
        //check internet and  data not loaded
        if(isConnected  && !isLoaded){
            isLoaded = false;
        }if(!isConnected && isLoaded){
            //offline check and  data loaded


        }else if(isConnected && isLoaded){
        }
    }












}
