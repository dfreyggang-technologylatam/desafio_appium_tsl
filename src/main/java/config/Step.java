package config;

/**
 * Mensajes de consola para seguir el flujo del test paso a paso.
 */
public final class Step {

    private Step() {
    }

    public static void info(String message) {
        System.out.println("[PASO] " + message);
    }

    public static void ok(String message) {
        System.out.println("[ OK ] " + message);
    }
}
