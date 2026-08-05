# Swag Labs — Automatización Mobile con Appium

Proyecto Maven con Appium + Java + TestNG para pruebas funcionales de la **Swag Labs Mobile App** (Android), usando Page Object Model (POM).

## Requisitos

- Java JDK 11+ (`JAVA_HOME` configurado)
- Android Studio / Android SDK (`ANDROID_HOME` configurado)
- Emulador Android API 33+ **o** dispositivo físico con USB debugging
- Node.js + Appium Server 2.x (o 3.x)
- Driver: `appium driver install uiautomator2`
- APK: `Android.SauceLabs.Mobile.Sample.app.apk` en la carpeta `apps/`
  - Descarga: https://github.com/saucelabs/sample-app-mobile/releases

## Verificación del entorno

```bash
appium --version
appium driver list --installed
adb devices
```

## Cómo ejecutar

1. Coloca el APK en `apps/Android.SauceLabs.Mobile.Sample.app.apk`
2. Arranca el emulador (o conecta el dispositivo)
3. En una terminal, inicia Appium:

```bash
appium
```

4. En otra terminal, desde la raíz del proyecto:

```bash
mvn clean test
```

## Estructura

```
├── pom.xml
├── .gitignore
├── apps/
│   └── Android.SauceLabs.Mobile.Sample.app.apk
├── src/main/java/
│   ├── config/          # BaseTest, CapabilitiesManager
│   └── pages/           # Page Objects
├── src/test/java/
│   └── tests/           # Casos de prueba
└── testng.xml
```

## Scripts de prueba

| Script | Descripción |
|--------|-------------|
| Login + carrito | Login `standard_user` y agregar producto al carrito |
| Login inválido | `locked_out_user` y mensaje de bloqueo |
| Orden High to Low | Validar orden de precios |
| Flujo E2E | Login → carrito → checkout → finish |
