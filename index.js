import React, { Component } from 'react'
import {
	UIManager,
	findNodeHandle,
	ViewPropTypes,
	requireNativeComponent,
} from 'react-native'
import { string, func, number } from 'prop-types'

class InMobiBanner extends Component {
	componentDidMount() {
		this.loadBanner()
	}

	loadBanner = () => {
		UIManager.dispatchViewManagerCommand(
			findNodeHandle(this._bannerView),
			UIManager.getViewManagerConfig('RNInMobiBannerView').Commands.loadBanner,
			null,
		)
	}

	onAdLoadFailed = (event) => {
		if (this.props.onAdLoadFailed) {
			this.props.onAdLoadFailed(event.nativeEvent.error)
		}
	}

	render() {
		return <RNInMobiBannerView
			{...this.props}
			onAdLoadFailed={this.onAdLoadFailed}
			ref={ref => this._bannerView = ref}
		/>
	}
}

const RNInMobiBannerView = requireNativeComponent('RNInMobiBannerView', InMobiBanner)

InMobiBanner.propTypes = {
	...ViewPropTypes,

	// props
	placementId: string,
	refreshInterval: number,

	// callback
	onAdLoadSucceeded: func,
	onAdLoadFailed: func,
	onAdDisplayed: func,
	onAdDismissed: func,
	onAdClicked: func,
	onRewardsUnlocked: func,
	onUserLeftApplication: func,
}

export default InMobiBanner
