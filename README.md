# MobWith Android SDK

MobWith SDK 를 이용하여 광고를 노출하는 방법을 제공하고 있습니다.  

# MobWith Android SDK Release History
 |version|Description|
|---|:---:|
|0.9.1|first Release|

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
        jcenter()
        mavenCentral()
    }
}
```

- app build.gradle 에 com.google.android.gms 와 MobWith 라이브러리를 추가합니다.
```XML
dependencies {
  implementation fileTree(dir: 'libs', include: ['*.jar'])
  implementation 'com.google.android.gms:play-services-ads-identifier:17.0.0'
  implementation 'io.github.mobon:mobWithSDK:0.9.1' 
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
 #[Adfit 바로가기](https://github.com/adfit/adfit-android-sdk) 


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
                       banner_container.addView(rv);
                } else {                
                    System.out.println("광고실패 : " + errorcode);
                    rv.destroyAd();
                    rv = null;                }
            }

            @Override
            public void onAdClicked() {
                System.out.println("광고클릭");
            }
        });
        
      
        
        // 광고를 호출합니다.
        rv.loadAd();

```
  
## 주의 사항

- Proguard를 적용하는 경우 proguard configuration 파일 수정이 필요합니다.  
자세한 구현 내용은 샘플 프로젝트의 `proguard.cfg ` 파일 또는 [proguard-rules.pro](/app/proguard-rules.pro) 참고해 주세요.
