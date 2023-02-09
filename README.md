# MobWith Android SDK

MobWith SDK 를 이용하여 광고를 노출하는 방법을 제공하고 있습니다.  

# MobWith Android SDK Release History
 |version|Description|
|---|:---:|
|0.9.7|MobwithNativeADView 추가|
|0.9.6|appLovin 연동 추가|
|0.9.2|first Release|

## 개발환경
- 최소 SDK Version : Android 23
- Compile SDK : Android 31 이상
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
  implementation 'io.github.mobon:mobwithSDK:0.9.7' 
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
- Adfit 광고를 송출하기 위해 링크를 참고하여 주세요.  
[Adfit SDK 바로가기](https://github.com/adfit/adfit-android-sdk) 

## 3. AppLovin SDK 추가
- AppLovin 광고를 송출하기 위해 아래와 같이 연동해 주세요.  
  - app build.gradle 에 Applovin sdk 추가
  ````
  dependencies {
    ...
    implementation 'com.applovin:applovin-sdk:11.6.0'
    }
  ````
  - AndroidManifest.xml 에 발급받은 sdk 추가
  ````
  <meta-data android:name="applovin.sdk.key"
             android:value={sdk_key}/>
  ````

##  배너 광고 예제

```java

LinearLayout banner_container = findViewById(R.id.banner_container);
// 각 광고 뷰 당 발급받은 UNIT_ID 값을 필수로 넣어주어야 합니다.
MobWithBannerView banner = new MobwithBannerView(this).setBannerUnitId(YOUR_UNIT_ID);

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


## 주의 사항

- Proguard를 적용하는 경우 proguard configuration 파일 수정이 필요합니다.  
자세한 구현 내용은 샘플 프로젝트의 `proguard.cfg ` 파일 또는 [proguard-rules.pro](/app/proguard-rules.pro) 참고해 주세요.
