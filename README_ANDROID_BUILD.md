# SAPHATECH PRO BOT — PHONE-ONLY Android Architecture

This revision removes the old Windows-PC/WebView controller architecture.

## Target architecture
Android phone -> Internet -> supported broker/API adapter -> Exness trading account

The Android app contains the UI, multi-timeframe strategy layer, position manager interface, and foreground trading service. It does NOT depend on localhost, Streamlit, a Windows PC, or a laptop.

## Important live-trading limitation
This project intentionally does not pretend that the ordinary MT5 Android app is an automation API. Exness documents MT5/Python algorithmic trading through MT5 on a computer, while its mobile trading apps provide manual trading. A real phone-only autonomous implementation therefore requires a broker-supported API/adapter or other explicitly supported execution interface. Do not enter MT5 passwords into an unsupported third-party form.

## Build
Open the project in Android Studio and build the debug APK. No Python or MT5 desktop installation is required to build/run the Android shell.

## Current modules
- MainActivity: phone-only trading dashboard
- TradingEngineService: persistent foreground engine service
- BrokerGateway: broker-neutral execution interface
- StrategyEngine: multi-timeframe strategy layer

The broker adapter and strategy rules are deliberately separated so live execution can be connected without tying credentials or unsupported MT5 automation into the UI.
