package config;

import io.appium.java_client.android.options.UiAutomator2Options;
import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;

/**
 * Centraliza las capabilities de la sesión Appium (Android / UiAutomator2).
 */
public final class CapabilitiesManager {

    private CapabilitiesManager() {
    }

    public static UiAutomator2Options getAndroidOptions() {
        String apkPath = Paths.get(System.getProperty("user.dir"), "apps",
                "Android.SauceLabs.Mobile.Sample.app.apk").toString();

        File apk = new File(apkPath);
        if (!apk.exists()) {
            throw new IllegalStateException("APK no encontrado en: " + apkPath);
        }

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName("emulator-5554");
        options.setUdid("emulator-5554");
        options.setApp(apk.getAbsolutePath());
        // La app sale rápido de SplashActivity; sin esto Appium falla al iniciar sesión
        options.setAppWaitActivity("*");
        options.setNewCommandTimeout(Duration.ofSeconds(120));
        options.setNoReset(false);
        return options;
    }

    public static String getAppiumServerUrl() {
        return "http://127.0.0.1:4723";
    }
}
