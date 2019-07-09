
package linj.lib.inmobi.banner;

import androidx.annotation.Nullable;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.view.ReactViewGroup;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.ads.listeners.BannerAdEventListener;

import java.util.Map;

class ReactAdView extends ReactViewGroup {

	protected InMobiBanner adView;

	protected long placementId;
	protected int refreshInterval = 0;

	public ReactAdView(final Context context) {
		super(context);
		this.createAdView();
	}

	public void createAdView() {
		adView = new InMobiBanner(getContext(), placementId);
		adView.setListener(new BannerAdEventListener() {
			@Override
			public void onAdLoadSucceeded(InMobiBanner banner) {
				sendEvent("onAdLoadSucceeded", null);
			}

			@Override
			public void onAdLoadFailed(InMobiBanner banner, InMobiAdRequestStatus status) {
				WritableMap event = Arguments.createMap();
				WritableMap error = Arguments.createMap();
				error.putString("reason", status.getMessage());
				event.putMap("error", error);
				sendEvent("onAdLoadFailed", event);
			}

			@Override
			public void onAdClicked(InMobiBanner ad, Map<Object, Object> params) {
				sendEvent("onAdClicked", null);
			}

			@Override
			public void onAdDismissed(InMobiBanner banner) {
				sendEvent("onAdDismissed", null);
			}

			@Override
			public void onAdDisplayed(InMobiBanner banner) {
				sendEvent("onAdDisplayed", null);
			}

			@Override
			public void onRewardsUnlocked(InMobiBanner ad, Map<Object, Object> rewards) {
				sendEvent("onRewardsUnlocked", null);
			}

			@Override
			public void onUserLeftApplication(InMobiBanner banner) {
				sendEvent("onUserLeftApplication", null);
			}
		});

		int width = toPixelUnits(320);
		int height = toPixelUnits(50);
		RelativeLayout.LayoutParams bannerLayoutParams = new RelativeLayout.LayoutParams(width, height);
		bannerLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		bannerLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		this.addView(adView, bannerLayoutParams);
	}

	private int toPixelUnits(int dp) {
		float density = this.getResources().getDisplayMetrics().density;
		return Math.round(density * dp);
	}

	private void sendEvent(String name, @Nullable WritableMap event) {
		ReactContext reactContext = (ReactContext) getContext();
		reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
			getId(),
			name,
			event
		);
	}

	public void setPlacementId(long id) {
		placementId = id;
	}

	public void setRefreshInterval(int interval) {
		refreshInterval = interval;
	}

	public void loadBanner() {
		adView.load();
		if (refreshInterval > 0) {
			adView.setRefreshInterval(refreshInterval);
		}
	}
}

public class RNInMobiBannerViewManager extends ViewGroupManager<ReactAdView> {

	public static final String REACT_CLASS = "RNInMobiBannerView";

	public static final String PROP_PLACEMENT_ID = "placementId";
	public static final String PROP_REFRESH_INTERVAL = "refreshInterval";

	public static final int COMMAND_LOAD_BANNER = 1;

	@Override
	public String getName() {
		return REACT_CLASS;
	}

	@Override
	protected ReactAdView createViewInstance(ThemedReactContext themedReactContext) {
		ReactAdView adView = new ReactAdView(themedReactContext);
		return adView;
	}

	@Override
	public void addView(ReactAdView parent, View child, int index) {
		throw new RuntimeException("RNInMobiBannerView cannot have subviews");
	}

	@Override
	@Nullable
	public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
		MapBuilder.Builder<String, Object> builder = MapBuilder.builder();
		String[] events = {
			"onAdLoadSucceeded",
			"onAdLoadFailed",
			"onAdClicked",
			"onAdDisplayed",
			"onAdDismissed",
			"onRewardsUnlocked",
			"onUserLeftApplication"
		};
		for (int i = 0; i < events.length; i += 1) {
			builder.put(events[i], MapBuilder.of("registrationName", events[i]));
		}
		return builder.build();
	}

	@ReactProp(name = PROP_PLACEMENT_ID)
	public void setPropPlacementId(final ReactAdView view, final String placementId) {
		view.setPlacementId(Long.parseLong(placementId));
	}

	@ReactProp(name = PROP_REFRESH_INTERVAL)
	public void setPropRefreshInterval(final ReactAdView view, final int refreshInterval) {
		view.setRefreshInterval(refreshInterval);
	}

	@Nullable
	@Override
	public Map<String, Integer> getCommandsMap() {
		return MapBuilder.of("loadBanner", COMMAND_LOAD_BANNER);
	}

	@Override
	public void receiveCommand(ReactAdView root, int commandId, @javax.annotation.Nullable ReadableArray args) {
		switch (commandId) {
			case COMMAND_LOAD_BANNER:
				root.loadBanner();
				break;
		}
	}
}