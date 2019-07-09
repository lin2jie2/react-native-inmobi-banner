
# react-native-inmobi-banner

## Getting started

`$ npm install react-native-inmobi-banner --save`

### Mostly automatic installation

`$ react-native link react-native-inmobi-banner`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import linj.lib.inmobi.banner.RNInMobiBannerPackage;` to the imports at the top of the file
  - Add `new RNInMobiBannerPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-inmobi-banner'
  	project(':react-native-inmobi-banner').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-inmobi-banner/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-inmobi-banner')
  	```


## Usage
```javascript
import RNInMobiBanner from 'react-native-inmobi-banner';

// TODO: What to do with the module?
RNInMobiBanner;
```

## 设置

### android/app/src/main/java/a.b.c/MainApplication.java

```
...
import com.inmobi.sdk.InMobiSdk;
...

public void onCreate() {
  ...
  InMobiSdk.init(this, "YOUR_ACCOUNT_ID");
  // InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
}
```

### android/app/src/main/AndroidManifest.xml

```

  <!-- required -->
  <uses-permission android:name="android.permission.INTERNET" />
  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>

  <!-- optional -->
  <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
  <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>

  <application ...>

  ...

  <!-- required -->
  <meta-data android:name="com.google.android.gms.version" android:value="@integer/google_play_services_version"/>
</application>
```

### android/app/build.gradle

```
...
dependencies {
  ...
  implementation 'com.inmobi.monetization:inmobi-ads:+'
  ...
}
...
```

## Demo

```
import React, { Component } from 'react'
import {
  View,
  Text,
  StyleSheet,
} from 'react-native'

import InMobiBanner from 'react-native-inmobi-banner'

export default class extends Component {
  onAdLoadSucceeded = () => {
    console.warn('onAdLoadSucceeded')
  }

  onAdDismissed = () => {
    console.warn('onAdDismissed')
  }

  onAdDisplayed = () => {
    console.warn('onAdDisplayed')
  }

  onAdLoadFailed = ({reason}) => {
    console.warn('onAdLoadFailed', reason)
  }

  onAdClicked = () => {
    console.warn('onAdClicked')
  }

  onRewardsUnlocked = () => {
    console.warn('onRewardsUnlocked')
  }

  onUserLeftApplication = () => {
    console.warn('onUserLeftApplication')
  }

  render() {
    return <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
      <InMobiBanner
        style={{backgroundColor: 'red', height: 50, width: 320}}
        placementId={"YOUR_PLACEMENT_ID"}
        refreshInterval={60}
        onAdLoadSucceeded={this.onAdLoadSucceeded}
        onAdLoadFailed={this.onAdLoadFailed}
        onAdDismissed={this.onAdDismissed}
        onAdDisplayed={this.onAdDisplayed}
        onAdClicked={this.onAdClicked}
        onRewardsUnlocked={this.onRewardsUnlocked}
        onUserLeftApplication={this.onUserLeftApplication}
      />
    </View>
  }
}
```

## 补充说明

广告只支持 320x50 。
由于我一直处于 ```onAdLoadFailed({reason: "Ad request successful but no ad served."})``` 状态。后续的相关内容没法继续。所以出问题要靠你自己解决。
