# MobWith Android SDK
MobWith SDK 를 이용하여 광고를 노출하는 방법을 제공하고 있습니다.  


## 최신 버전 및 변경사항
- 최신버전 : 1.0.41
- 변경사항
  - MobwithWhoWhoPointBannerBridge 기능 수정
<br>

## 개발환경
- 최소 SDK Version : Android 23
- Compile SDK : Android 34
- Build Tool : Android Studio 
- androidX 권장
 
## 1. MobWith SDK 기본설정

- project build.gradle 에 mavenCentral() 을 추가합니다.

```XML
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
```

- app build.gradle 에 com.google.android.gms 와 MobWith 라이브러리를 추가합니다.
```XML
dependencies {
  implementation fileTree(dir: 'libs', include: ['*.jar'])
  implementation 'com.google.android.gms:play-services-ads-identifier:17.0.0'
  implementation 'io.github.mobon:mobwithSDK:1.0.41' 
}
```

** Android 9 (Pie) 업데이트에 따른 추가설정

- targetSdkVersion 28 부터 네트워크 통신 시 암호화 되지 않은 HTTP통신이 차단되도록 기본설정이
변경되었습니다. SDK 내 모든 미디에이션 광고가 정상동작하기 위해서는 HTTP 통신을 허용해주셔야 하며, 방법은 아래와 같습니다.

AndroidManifest.xml 파일에서 application 항목의 속성값으로
usesCleartextTraffic을 true로 설정해야 합니다.
(Android 9 부터 해당값이 default로 false 설정되어 HTTP 통신이 제한됩니다.)

```
<manifest ...>
<application
...
android:usesCleartextTraffic="true"
...>
</application>
</manifest>
```


## 2. ADFIT SDK 추가
- Adfit 광고를 송출하기 위해 링크를 참고하여 주세요.  <br>
  [Adfit SDK 바로가기](https://github.com/adfit/adfit-android-sdk) 
- 현재 3.15.2버전에 최적화 되어 있습니다.
- 경우에 따라서는 가이드에 안내된 "com.kakao.adfit:ads-base:3.15.2"가 아닌 다른 버전/타입의 SDK를 추가해야 할 수도 있습니다. <br>
  (해당되는 경우 별도로 안내됩니다.)


## 3. ADOP BidMad SDK 추가  
- ADOP 광고를 송출하기 위해 링크를 참고하여 주세요. (3.18.0 버전에 최적화 되어 있습니다.) <br>
[ADOP BidMad SDK 바로가기](https://github.com/bidmad/Bidmad-Android/blob/master/README.md#1-SDK-%EC%84%B8%ED%8C%85) 
- SDK 세팅 부분만 참고하시면되며, API키등 설정해줘야 하는 값들은 협의된 내용을 토대로 적용하시면 됩니다.

## 4. Coupang SDK 추가
- Gradle 설정
  - 먼저 프로젝트 단위의 Gradle에 아래를 참고하여 CoupangSDK를 가져오기 위한 저장소를 추가해줍니다.
    ```XML
    buildscript {
      ...
      repositories {
          ...
          maven { url "https://raw.githubusercontent.com/coupang-ads-sdk/android/main" }
          ...
      }
    }
    
    ...
    

    allprojects {
      repositories {
          ...
          maven { url "https://raw.githubusercontent.com/coupang-ads-sdk/android/main" }
          ...
      }
    }
    ```

  - 다음으로 App단위의 Gradle 파일에 아래와 같이 Coupnag SDK를 Implements 해주시면 됩니다.
    ```XML
    implementation 'com.coupang:ads:1.3.0'
    ```
- AdnroidMenifest.xml에서 아래와 같이 applicaion태그 내부에 meta-data를 추가해 줍니다. 넣어야 할 값은 가이드와 함께 제공된 Coupang Sub ID 값을 참고 하시면 됩니다.
  ```XML
    <application>
      ....
      <meta-data android:name="coupang_ads_sub_id"
            android:value="{전달 받은 Coupnag Sub ID}"/>
      ....
    </application>
  ```


- Proguard 설정  
  Proguard 사용시 아래와 같이 예외 설정을 추가해 주셔야 합니다.
  ```XML
    -keep interface com.coupang.ads.dto.DTO
    -keep class * implements com.coupang.ads.dto.DTO { *; }
  ```
  
- 주의 및 참고 사항
  - 광고View들을 생성시 전달하는 Context는 LifecycleOwner를 상속 또는 Implements하고 있어야 합니다.  
    대체로 AndroidX의 ComponentActivity를 상속받고 있는 객체라면 특별히 문제 되지는 않습니다.
  - 전면배너의 경우 사이즈 옵션과는 무관하게 Coupang SDK에서 지원하는 사이즈로만 출력됩니다.

<br>
<br>

## 5. Unity Ads SDK 추가
- Unity Ads 광고를 송출하기 위해 링크를 참고하여 주세요. (4.7.0 버전에 최적화 되어 있습니다.) <br>
[Unity Ads SDK 바로가기](https://docs.unity.com/ads/ko-kr/manual/AndroidDeveloperIntegrations) 
- SDK 연동 요구사항 및 Unity Ads SDK를 설치하기 위한 부분을 참고 하시면 됩니다.
- GameID 설정  
  Unity Ads SDK를 미디에이션 하기위해서는 광고 로딩전 아래와 같이 GameID의 설정이 필요합니다.
  ```java
  MobwithSDK.getInstance().setUnityGameId(this,"{ 전달 받은 GameId }");
  ```   

<br>
<br>

## 6. IronSource(LevelPlay) SDK 추가
- IronSource(LevelPlay) 광고를 송출하기 위해 링크를 참고하여 주세요. (8.4.0 버전에 최적화 되어 있습니다.) <br>
[IronSource(LevelPlay) SDK 바로가기](https://developers.is.com/ironsource-mobile/android/getting-started-android/) 
- Getting started와 Adnroid SDK Integration 항목을 참고 하시면 됩니다.
- AppKey 설정  
  IronSource(LevelPlay) SDK를 미디에이션 하기위해서는 광고 로딩전 아래와 같이 AppKey의 설정이 필요합니다.
  ```java
  MobwithSDK.getInstance().setLevelPlayAppKey(this,"{ 전달 받은 AppKey }");
  ```


<br>
<br>
<br>


##  배너 광고 예제
```java

LinearLayout banner_container = findViewById(R.id.banner_container);
// 각 광고 뷰 당 발급받은 UNIT_ID 값을 필수로 넣어주어야 합니다.
MobWithBannerView banner = new MobwithBannerView(this)
                                      .setBannerUnitId(YOUR_UNIT_ID)
                                      .setInterval(60);

// 배너뷰의 리스너를 등록합니다.
banner.setAdListener(new iBannerCallback() {
  @Override
  public void onLoadedAdInfo(boolean result, String errorcode) {
    if (result) {
      //배너 광고 로딩 성공
      System.out.println("배너 광고로딩");
      
      // 광고를 띄우고자 하는 layout 에 배너뷰를 삽입합니다.
      banner_container.addView(banner);
    } else {
      System.out.println("광고실패 : " + errorcode);

      // 광고 로딩 실패시 동작 - setInterval()을 통해 자동 갱신을 설정했어도 실패한 경우 갱신되지 않음.
      banner.destroyAd();
      banner = null;     
    }
  }

  @Override
  public void onAdClicked() {
    System.out.println("광고클릭");
  }
  
});

// 광고를 호출합니다.
banner.loadAd();

```
### 1) setInterval() 
  해당 함수를 통해 배너 광고를 일정 시간마다 자동으로 갱신되도록 할 수 있습니다.  
  설정되는 값은 '초(Second)'단위로 설정해 주셔야 하며, 최소값은 60초로 60보다 작은 값을 설정하는 경우 60으로 설정 됩니다.  
  다만, 0으로 설정하는 경우 0으로 설정이 되며, 자동 갱신 기능이 동작하지 않습니다.  
  기본 값은 0이며, 설정후 광고를 처음 한번 로딩시켜야 동작합니다.  


### 2) stop(), restart()
  위 두 함수를 통해 광고 자동 갱신을 정지/재시작 할 수 있습니다.  
  특정 상황에 따라 뷰 내에서 직접 광고의 자동갱신 여부를 결정하고 있지만, 명시적으로 자동 갱신 여부를 결정할때 사용하면 됩니다.  
  setInterval()에서 0으로 설정한 경우 자동 갱신 정지/재시작은 동작하지 않습니다.  
  해당 함수들의 호출 및 동작은 setInterval()을 통해 광고 갱신이 되도록 설정 한뒤 광고를 처음 한번 로딩을 해주어야 합니다.

### 3) 광고 수신 실패시
  setInterval()을 통해 광고 자동 갱신 기능을 설정 하였어도 광고 수신을 실패한 경우 이후 부터는 광고가 자동으로 갱신되지 않으니 주의 바랍니다.
  해당 상황 발생시 직접 loadAd()를 다시 호출해줘야 광고를 다시 불러올수 있으니 참고 하시면 됩니다.

### 4) destroyAd()
  본 함수를 호출하여 광고 객체들을 초기화 시켜줄 수 있습니다. 자동 갱신등이 설정된 경우 동작을 멈추게 됩니다.
  따라서 해당 배너뷰를 사용하지 않게 되는 경우 호출는 것을 권장 드립니다.

<br>
<br>


## 광고뷰의 크기 설정
광고의 크기는 노출되는 광고의 크기에 따라 자동으로 변경됩니다.  
따라서 광고를 표시할 뷰의 레이아웃을 아래를 참고하여 설정을 해주어야 광고가 이상없이 출력됩니다.

```java
....
<LinearLayout
  android:id="@+id/banner_container"
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  android:layout_alignParentBottom="true"
  android:layout_centerHorizontal="true"
  android:gravity="center"
  android:orientation="vertical"
  />
....

```
<br>
<br>

##  MobwithNativeAdView 광고 예제

MobwithNativeAdView는 사용자가 직접 뷰를 설정하고, 설정된 뷰를 SDK에서 전달받아 각각의 view에 광고 데이터를 설정해주는 기능만 담당하는 AdView입니다.

### 광고 로드 방법
```java
....
nativeAdView = new MobwithNativeAdView(this,
                adUnitID,
                (FrameLayout) findViewById(R.id.adview_container),
                R.layout.custom_native_ad_view,
                R.id.mediaContainerView,
                R.id.imageViewAD,
                R.id.imageViewLogo,
                R.id.textViewTitle,
                R.id.textViewDesc,
                R.id.buttonGo,
                R.id.infoViewLayout,
                R.id.imageViewInfo);

nativeAdView.loadAd();
....
```

* adview_container에서 아래 각 View의 id를 확인하지 못하게 되는 경우 광고가 제대로 표시되지 않을 수 있으니 주의하시기 바랍니다.

* 위 예시에서 mediaContainerView는 GroupView중 하나여야 하며, imageViewAD를 포함하고 있는 구조 입니다.  
  미디에이션을 지원하는 외부 SDK중 Native AD를 제공하는 SDK 마다 서로 다른 규격을 요구하는 부분 때문이니 주의 바랍니다.  

### 광고 클릭 버튼을 사용하지 못하는 경우
```java
....
nativeAdView.performAdClicked();
....
```
위 메소드를 호출하여 광고를 클릭한 것과 동일한 효과를 줄 수 있습니다.  
<br>
<br>
  
##  MobwithNativeAdLoader 광고 예제
MobwithNativeAdLoader는 NativeAdView를 리스트 타입의 뷰에 노출하고자 할 때 적용 가능한 기능 입니다.

### 1. 광고 로드 방법

먼저 광고를 불러오기 위해 MobwithNativeAdLoader를 생성 및 초기화를 진행해 줍니다.
```java

//MobwithNativeAdLoader 생성
String[] adUnitIDs = new String[] { "광고 Unit ID" };   //1개 이상의 Unit를 설정해 주어야 합니다.

MobwithNativeAdLoader adLoader = new MobwithNativeAdLoader(this, adUnitIDs);
adLoader.setAdListener(new iNativeBannerCallback() {

  @Overridepublic void onLoadedAd(int index, boolean result, String errorStr) {
    if (result) {
      RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
      recyclerView.getAdapter().notifyItemChanged(index);
    }
    ...
  }

  @Override
  public void onAdClicked(int index) {
    // 광고 클릭시
  }

});

//광고를 표시할 View 설정
adLoader.setNativeADView(this,
      R.layout.custom_native_ad_view,
      R.id.mediaContainerView,
      R.id.imageViewAD,
      R.id.imageViewLogo,
      R.id.textViewTitle,
      R.id.textViewDesc,
      R.id.buttonGo,
      R.id.infoViewLayout,
      R.id.imageViewInfo);


.......

```
이후 RecyclerView등 리스트 타입 View의  Adapter 클래스에서 다음과 같이 광고View를 받아와서 화면에 표시해 줍니다.

```java

...

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
  
  if (position % 5 == 4) {

    // loadAD를 호출하면 이미 로드된 광고가 있거나 이미 생성된 뷰가 있는 경우 해당 View를 전달해 줍니다.
    ViewGroup adView = adLoader.loadAD(NativeAdLoaderTestActivity.this, position);
    

    // adLoader.isLoadedAd(position)을 호출하면 광고를 받아온 경우 true를 반환합니다. 해당 값을 확인후 뷰에 추가하는것을 권장드립니다.
    if (adView != null && adLoader.isLoadedAd(position) ) {
      holder.frameLayout.addView(adView);
    }
    ....
  }
  ...

}
...

```
* 더 자세한 사항은 Sample앱을 참고 하시기 바랍니다.
<br>
<br>


## 전면배너 광고 예제


``` java
InterstitialDialog interstitialDialog = new InterstitialDialog(this).setBackCancel(true).setType(Key.INTERSTITIAL_TYPE.FULL).setUnitId("광고 Unit ID").build();
interstitialDialog.setAdListener(new iInterstitialCallback() {
  @Override
  public void onLoadedAdInfo(boolean result, String errorStr) {
    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override
      public void run() {
        if (result) {
          Toast.makeText(MainActivity.this, "광고 로드 성공(FULL)", Toast.LENGTH_SHORT).show();
          }
        }
      });
  }

  @Override
  public void onClickEvent(Key.INTERSTITIAL_KEYCODE keyCode) {
    if (keyCode == Key.INTERSTITIAL_KEYCODE.CLOSE_AD) {
      System.out.println("전면 닫음(FULL)");
      interstitialFullDialog.load();
    }
    else if (keyCode == Key.INTERSTITIAL_KEYCODE.ADCLICK) {
      System.out.println("전면 광고 클릭(FULL)");
    }
  }


  @Override
  public void onOpened() {
    System.out.println("전면 오픈(FULL)");
  }

  @Override
  public void onClosed() {
    System.out.println("전면 닫음(FULL)");
  }              

  @Override
  public void onFailOpened() {
    //전면 배너 광고 오픈 실패
  }

});

interstitialDialog.load();

```
전면배너는 광고를 로딩후 로딩이 완료된 시점 이후에 광고를 표시하여야 합니다.  
위 예시에서는 광고를 로딩하는 부분까지만 소개된 것 입니다.  
위에서 광고 로딩을 성공한 경우 아래와 같이 호출하여 광고를 화면에 출력할 수 있습니다.

```java
interstitialDialog.show();
```


### 전면배너 광고 사이즈 별 타입

 | Size | Type Constant | Description | 
 |---|:---:|:---:|
 | NORMAL | INTERSTITIAL_TYPE.NORMAL | 일반사이즈의 전면배너 광고(화면을 꽉 채우지 않음) |
 | FULL | INTERSTITIAL_TYPE.FULL | 전체화면을 꽉 채우는 형태의 전면배너광고 |  

<br>
<br>

## 엔딩배너 광고 예제

``` java
EndingDialog endingDialog = new EndingDialog(this).setBackCancel(false).setUnitId(bannerUnitID_Interstitial).build();
endingDialog.setAdListener(new iInterstitialCallback() {
  @Override
  public void onLoadedAdInfo(boolean result, String errorStr) {
    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override
      public void run() {
        if (result) {
          Toast.makeText(MainActivity.this, "광고 로드 성공(ENDING)", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  @Override
  public void onClickEvent(Key.INTERSTITIAL_KEYCODE keyCode) {
    if (keyCode == Key.INTERSTITIAL_KEYCODE.CLOSE_AD) {
      //엔딩 - 닫음
    }
    else if (keyCode == Key.INTERSTITIAL_KEYCODE.ADCLICK) {
      //엔딩 - 광고 클릭
    }
    else if (keyCode == Key.INTERSTITIAL_KEYCODE.CLOSE_APP) {
      //엔딩 - 종료 버튼 클릭
      }
    }
    
    @Override
    public void onOpened() {
      //엔딩 광고 오픈
    }

    @Override
    public void onClosed() {
      //엔딩 광고 닫음
    }

    @Override
    public void onFailOpened() {
      //엔딩 광고 오픈 실패
    }
  });

```
엔딩 배너의 경우 전면배너와 유사한 형태로 동작하게 됩니다.  
광고 로딩이 완료되면 아래와 같이 호출하여 엔딩배너를 표시할 수 있습니다.

```java
endingDialog.show();
```

<br>

## 전면/엔딩 배너 Click Event KeyCode
전면 / 엔딩배너의 onClickEvent에서 내려오는 KeyCode는 아래와 같습니다.

 | KeyCode | Description | 
 |---|:---:|
 | CLOSE_AD | 광고창을 닫는 버튼을 눌렀을때(앱 종료 아님) |
 | ADCLICK | 광고를 클릭한 경우 |  
 | CLOSE_APP | 앱 종료 버튼을 눌렀을때 |  


<br>
<br>

## 뉴스피드 배너 예제
한줄 기사가 표시되는 텍스트형 배너 뷰 입니다.<br>
해당 뷰의 경우 높이값 30dp에 맞게 UI가 구현되어 있으며, 해당 지면의 높이값이 30dp 보다 작은 경우 화면에 UI가 제대로 표시되지 않을 수 있습니다.

``` java
MobwithArticleBannerView adArticleBannerView =  new MobwithArticleBannerView(this)
adArticleBannerView.setAdListener(new iArticleBannerCallback() {

            @Override
            public void onLoadedArticles(boolean result, String errorStr) {
                System.out.println("[MobwithArticleBannerView] onLoadedArticles() : "  +result);
            }

            @Override
            public void onArticleClicked() {
                System.out.println("[MobwithArticleBannerView] onArticleClicked()");
            }

        });
banner_container.addView(adArticleBannerView);

adArticleBannerView.loadAd();
```



## BannerWithArticleView 예제
광고 및 컨텐츠가 제대로 표시되기 위해서는 반드시 해당 뷰의 높이가 100dp 이상이어야 합니다. 가로 사이즈의 경우 해당 뷰가 추가되는 부모뷰의 사이즈를 따라갑니다.
권장하는 뷰 사이즈는 360x100이며, 가로 사이즈의 경우 320 이상을 권장드립니다.
사용 방법은 대체로 MobwithBannerView와 동일합니다.
``` java

LinearLayout banner_container = findViewById(R.id.banner_container);
// 각 광고 뷰 당 발급받은 UNIT_ID 값을 필수로 넣어주어야 합니다.
MobwithBannerWithArticleView banner = new MobwithBannerWithArticleView(this)
                                          .setBannerUnitId(YOUR_UNIT_ID)
                                          .setInterval(60);

// 배너뷰의 리스너를 등록합니다.
banner.setAdListener(new iBannerCallback() {
  @Override
  public void onLoadedAdInfo(boolean result, String errorcode) {
    if (result) {
      //배너 광고 로딩 성공
      System.out.println("배너 광고로딩");
      
      // 광고를 띄우고자 하는 layout 에 배너뷰를 삽입합니다.
      banner_container.addView(banner);
    } else {
      System.out.println("광고실패 : " + errorcode);

      // 광고 로딩 실패시 동작 - setInterval()을 통해 자동 갱신을 설정했어도 실패한 경우 갱신되지 않음.
      banner.destroyAd();
      banner = null;     
    }
  }

  @Override
  public void onAdClicked() {
    System.out.println("광고클릭");
  }
  
});

// 광고를 호출합니다.
banner.loadAd();

``` 

<br>
<br>
<br>


## MobwithFreePassAdView 예제
해당 광고뷰는 광고 클릭시 일정기간동안 광고를 보여주지 않도록 하는 상품에 대응하는 AdView 입니다.
실제 광고 미노출 기간에 대한 처리는 직접 진행해 주셔야하며, 이에 대해 필요한 기능을 제공 합니다.

기본 광고 로딩을 위한 방법은 일반 배너광고(MobWithBannerView)와 거의 동일하며 아래와 같습니다.
``` java

LinearLayout banner_container = findViewById(R.id.banner_container);
// 각 광고 뷰 당 발급받은 UNIT_ID 값을 필수로 넣어주어야 합니다.
MobwithFreePassAdView bannerView = new MobwithFreePassAdView(this)
                                              .setBannerUnitId(YOUR_UNIT_ID);                                            

// 광고 매체 이름을 지정 합니다.
//현재 기본값으로 '후후'가 적용되어 있으며, 해당값 지정시 광고뷰 상단의 '##와 함께하는 광고 프리패스'에서 '##'부분이 변경됩니다.
freePassAdView.setMcName("{광고 매체 이름}");

// 광고 매체 로고 이미지를 적용합니다.
// 광고부 좌측 상단에 로고 이미지가 표시되며, 높이값은 10dp로 고정됩니다. 
// 현재 기본값으로 후후 로고가 적용되어 있으므로, 별도로 지정하지 않으면 후후로고가 표시됩니다.
freePassAdView.setMcLogoImage(getResources().getDrawable(com.mobwith.sdk.R.drawable.logo_whowho));


//프리패스 배너뷰의 Listener를 등록합니다.  MobWithBannerView와 달리 iFreePassBannerCallback을 등록해주셔야 합니다.
freePassAdView.setAdListener(new iFreePassBannerCallback() {
    @Override
    public void onLoadedAdInfo(boolean result, String errorStr) {
        System.out.println("[MobwithFreePassAdViewTest] onLoadedAdInfo :  "  +result);
        if (result) {
            // 광고 로딩 성공
            updateUI(() -> {
                if (freePassAdView.getParent() == null) {
                    banner_container.addView(freePassAdView);
                }
            });
        }
        else {
            // 광고 로딩 실패
            clearAdView();
        }
    }

    @Override
    public void onAdClicked(Date startLandingDate) {
        // 광고 클릭시 호출됨. 광고 클릭 시점의 시간을 전달해 준다.
        updateLastClickedTime();
    }
});

// 광고를 호출합니다.
bannerView.loadAd();

``` 

광고 클릭시 onAdClicked() 함수를 통해 광고 클릭 시간을 전달해주며, 해당 값을 사용하여 광고 미노출 시간을 제어하시면 됩니다.
만약, 광고 클릭 시간을 별도로 확인이 필요한 경우 아래 함수를 통해 확인 가능 합니다.

```java 
  Date clickedTime = MobwithFreePassAdView.getLastAdClickedTime(this)
```




<br>
<br>
<br>

## MobwithPointBannerView 예제
해당 광고뷰는 광고 클릭시 리워드를 제공하기 위한 기능 및 UI가 추가된 배너뷰 입니다.  
리워드 제공에 대한 각 기능들은 직접 구현해 주셔야 하며, 현재는 클릭 또는 노출시 리워드 제공에 대한 기능만 고려된 관계로  
리워드와 관련된 별도의 콜백은 제공되고 있지 않습니다.  
해당 부분은 광고 로딩 성공과 광고 클릭시 전달되는 콜백 이벤트를 활용하시면 됩니다.

기본 광고 로딩을 위한 방법은 일반 배너광고(MobWithBannerView)와 거의 동일하며 아래와 같습니다.

``` java
LinearLayout banner_container = findViewById(R.id.banner_container);

// 각 광고 뷰 당 발급받은 UNIT_ID 값을 필수로 넣어주어야 합니다.
MobwithPointBannerView bannerView = new MobwithPointBannerView(this).setBannerUnitId(YOUR_UNIT_ID);                                            


//리워드 제공 가능 여부를 설정합니다.
//SDK에서는 관련 UI를 제어하는 부분만 담당하며, 실제 리워드 제공에 대해서는 아래 Listener에서 직접 처리해야 합니다.
//설정값은 다음과 같습니다
// - true  : 리워드를 제공할 수 있는 상태 
// - false : 리워드를 제공할 수 없는 상태 (이미 리워드를 받아간 경우, 리워드 제한등)
pointBannerView.setPointEnable(true);

pointBannerView.setAdListener(new iBannerCallback() {
  @Override
  public void onLoadedAdInfo(boolean result, String errorcode) {
      if (result) {
      //배너 광고 로딩 성공
      System.out.println("배너 광고로딩");
      
      //광고를 띄우고자 하는 layout에 배너뷰를 삽입합니다.
      banner_container.addView(bannerView);
    } 
    else {
      System.out.println("광고실패 : " + errorcode);

      // 광고 로딩 실패시 동작 - setInterval()을 통해 자동 갱신을 설정했어도 실패한 경우 갱신되지 않음.
      bannerView.destroyAd();
      bannerView = null;     
    }
  }

  @Override
  public void onAdClicked() {
      if(bannerView != null) {
          bannerView.setPointEnable(false);
      }
      
      ...
      //실제 리워드 제공을 위한 동작 구현.
      ...
  }

});

// 광고를 호출합니다.
bannerView.loadAd();

``` 


<br>
<br>
<br>


## MobwithMultiPointBannerView 예제
기능은 MobwithPointBannerView와 동일하나, 하나의 광고만 표시하던 MobwithPointBannerView와 달리,
최대 5개의 광고를 좌우 스크롤하여 표시해주는 BannerView 입니다.
다만, 포인트 제공 가능 여부등 일부 기능의 경우 SDK 자체적으로 처리해주는 부분이 있으며, 해당 부분의 변경이 필요하신 경우 별도로 문의를 해주셔야 합니다.

광고의 적용은 기존 BannerView들과 거의 유사하며, 아래 내용을 참고하시면 됩니다.
``` java
LinearLayout banner_container = findViewById(R.id.banner_container);


MobwithMultiPointBannerView bannerView = new MobwithMultiPointBannerView(this)

// 광고 ID의 경우 아래와 같이 배열의 형태로 넣어주셔야하며, 최대 5개까지 적용 가능 합니다.
String[] bannerUnitIDs = new String[] { 
    "YOUR_UNIT_ID_1",
    "YOUR_UNIT_ID_2",
    "YOUR_UNIT_ID_3",
    "YOUR_UNIT_ID_4",
    "YOUR_UNIT_ID_5"
};
bannerView.setBannerUnitIds(bannerUnitIDs);

// 기존과는 다르게 광고View를 미리 추가해 줍니다.
banner_container.addView(bannerView);

pointBannerView.setAdListener(new iMultiPointBannerCallback() {

    @Override
    public void onLoadedAdInfo(boolean result, String errorStr) {
        // 광고 로딩 성공/실패 여부가 여기에 전달 됩니다.
    }

    @Override
    public void onAdClicked(int index, boolean isRewarded) {
        // 광고를 클릭한 경우 메세지가 전달 됩니다.
        // index : 클릭된 광고의 index
        // isRewarded : 이미 리워드를 제공한 광고인지 여부, 이미 포인트가 제공된 광고인 경우 true
        
        // 여기에서 리워드 제공 여부 판단, 리워드를 제공한 이후 아래 함수 호출.
        pointBannerView.setRewarded(index);
    }
});


// 광고를 호출합니다.
// 추가로, 포인트 제공 가능 여부의 리셋 기준을 만족하더라도 
// 아래 함수가 호출되지 않으면 UI가 변경되지 않으니 해당 부분 참고 바랍니다.
bannerView.loadAd();

``` 

<br>
<br>

## MobwithWhoWhoPointBannerBridge 예제
웹뷰 기반의 하이브리드 앱에서 MobwithPointBannerView와 동일한 광고를 추가하기 위해 필요한 기능이 구현되어 있습니다.  
사용 방법은 아래를 참고하시면 됩니다. 

### 1. MobwithWhoWhoPointBannerBridge 객체를 생성합니다.
```JAVA
MobwithWhoWhoPointBannerBridge mobwithPointBannerBridge;
WebView mWebView;
...

protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  ...
  
  WebSettings settings = mWebView.getSettings();
  //JavaScript 사용을 위해 webview에서 해당 옵션을 true로 설정해 줍니다.
  settings.setJavaScriptEnabled(true);

  //Context는 Activity Context를 필요로 합니다.
  mobwithPointBannerBridge = new MobwithWhoWhoPointBannerBridge(this, mWebView);

  ...
}

```
<br>

### 2. User ID 및 Frame Element ID 설정
```JAVA
// User ID는 포인트 적립 대상을 구분하기 위한 사용자 구분값 입니다.
mobwithPointBannerBridge.setUserID("{User Id}");

// FrameElementId는 웹 페이지 내에서 광고가 표시될 위치의 element id 값 입니다.
mobwithPointBannerBridge.setFrameElementId("{Frame Element Id}");

```
<br>

### 3. WebViewClient 설정
페이지 로딩이 완료되었음을 MobwithWhoWhoPointBannerBridge 객체에 전달해 주셔야 합니다.
``` JAVA
mWebView.setWebViewClient(new WebViewClient() {
  
  @Override
  public void onPageFinished(WebView view, String url) {
    super.onPageFinished(view, url);

    mobwithPointBannerBridge.onPageLoaded();
  }
  
});

```
<br>

### 4. 페이지 로딩
위 설정이 모두 끝난뒤에 WebView에 페이지를 로딩합니다.
```JAVA

mWebView.loadUrl(bannerUrl);

```
<br>

### 5. destroy() 함수 호출
MobwithWhoWhoPointBannerBridge를 사용하는 WebView 및 Activity가 파괴되는 시점에 
destroy() 를 호출해 주셔야 합니다.  여기에서는 Activity가 Destroy되는 시점에 호출하고 있습니다.

```JAVA
@Override
protected void onDestroy() {
    super.onDestroy();
    if (mobwithPointBannerBridge != null) {
        mobwithPointBannerBridge.destroy();
    }
}
```




<br>
<br>

## MobwithRewardVideoDialog 예제
광고를 본 사용자에게 리워드를 지급하기 위한 광고 입니다.
전면배너와 유사항 형태로 광고가 표시되며, 실제 사용방법도 전면배너와 거의 동일합니다.

광고를 호출하는 방법은 아래와 같습니다.
``` java

MobwithRewardVideoDialog rewardVideoDialog = new MobwithRewardVideoDialog(this).setUnitId("YOUR_UNIT_ID").build();

// 콜백을 받기위한 Listener는 iRewardAdsCallback를 사용합니다.
rewardVideoDialog.setAdListener(new iRewardAdsCallback() {
  @Override
  public void onLoadedAdInfo(boolean result, String errorStr) {
      if (result) {
        // 광고 로딩 성공
      } 
      else {
        // 광고 로딩 실패
      }
  }

  @Override
  public void onAdClicked() {
    // 광고를 클릭한 경우
  }

  @Override
  public void onOpened() {
    // 로드된 광고가 화면에 표시된 경우
  }

  @Override
  public void onFailOpened() {
    // 로드된 광고를 화면에 표시하지 못한경우.
  }

  @Override
  public void onClosed() {
    // 광고 창을 닫은 경우
      LogPrint.d("RewardAdTestActivity", "onClosed");
  }

  @Override
  public void onSkip() {
    // 광고를 끝까지 보지 않고 스킵한 경우
  }

  @Override
  public void onReward() {
    // 각 광고별로 리워드 조건을 충족한 경우
  }
});

// 광고 데이터를 로드합니다.
rewardVideoDialog.load();

// onLoadedAdInfo에서 result가 성공일 경우
// isLoaded() 함수를 통해 로드된 광고가 존재하는지 확인 할 수 있습니다.
if (rewardVideoDialog.isLoaded()) {
  rewardVideoDialog.show();   //화면에 광고를 표시합니다.
}

```




<br>
<br>
<br>
<br>
<br>




## 주의 사항

- Proguard를 적용하는 경우 proguard configuration 파일 수정이 필요합니다.  
자세한 구현 내용은 샘플 프로젝트의 `proguard.cfg ` 파일 또는 [proguard-rules.pro](/app/proguard-rules.pro) 참고해 주세요.

<br>
<br>
<br>
<br>
<br>
<br>


# MobWith Android SDK Release History
 | version |        Description        |
 | :-----: | :------------------------ |
 | 1.0.41  |  MobwithWhoWhoPointBannerBridge 기능 수정           |
 | 1.0.40  |  후후 간편적립 배너를 위한 MobwithWhoWhoPointBannerBridge 추가           |
 | 1.0.39  |  전면, 엔딩, 리워드 등 전체화면을 띄우는 광고에 onFailOpened() 콜백 추가.           |
 | 1.0.38  |  BugFix           |
 | 1.0.37  |  LevelPlay(IronSource) SDK의 사용이 강제되는 문제 수정 |
 | 1.0.36  |  MobwithRewardVideoDialog 추가 |
 | 1.0.35  |  Unity Ads SDK, LevelPlay(IronSource) SDK 추가 |
 | 1.0.34  |  MobwithPointBannerView, MobwithMultiPointBannerView 추가 |
 | 1.0.33  |  Coupang SDK의 NativeAd 기능 추가 |
 | 1.0.32  |  BugFix           |
 | 1.0.31  |  BugFix           |
 | 1.0.30  |  BugFix           |
 | 1.0.29  |  BugFix           |
 | 1.0.28  |  BugFix           |
 | 1.0.27  |  BugFix, AdMob 버전 교체           |
 | 1.0.26  |  MobwithFreePassAdView 추가           |
 | 1.0.25  |  BugFix           |
 | 1.0.23  |  BugFix           |
 | 1.0.22  |  BugFix           |
 | 1.0.21  |  Coupang SDK 추가, Bug Fix  |
 | 1.0.20  |  BugFix           |
 | 1.0.19  |  BugFix           |
 | 1.0.18  |  BugFix           |
 | 1.0.17  |  BannerWithArticleView 추가     |
 | 1.0.16  |  AppLovin 제거, AdFit SDK 버전 업데이트     |
 | 1.0.15  |  BugFix           |
 | 1.0.14  |  BugFix           |
 | 1.0.13  |  BugFix           |
 | 1.0.12  |  BugFix           |
 | 1.0.11  |  BugFix           |
 | 1.0.10  |  ADOP, AdFit배너 광고 연동 추가 및 광고 지원 타입 추가  |
 | 1.0.9   |  BugFix           |
 | 1.0.8   |  BugFix           |
 | 1.0.7   |  MobwithBannerView 자동갱신 기능 추가           |
 | 1.0.6   |  BugFix           |
 | 1.0.5   |  뉴스피드 배너 MobwithArticleBannerView 추가 |
 | 1.0.4   |  BugFix           |
 | 1.0.3   |  BugFix           |
 | 1.0.2   |  BugFix           |
 | 1.0.0   |  BugFix           |
 | 0.9.14  |  BugFix           |
 | 0.9.13  |  BugFix           |
 | 0.9.12  |  BugFix           |
 | 0.9.11  |  전면/엔딩배너, 배너 사이즈(320x100) 추가 |
 | 0.9.10  |  MobwithNativeAdLoader 추가 |
 | 0.9.9   |  BugFix           |
 | 0.9.7   |  MobwithNativeADView 추가  |
 | 0.9.6   |  appLovin 연동 추가     |
 | 0.9.2   |  first Release        |
