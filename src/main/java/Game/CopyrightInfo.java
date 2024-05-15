package Game;

public class CopyrightInfo {
    // 私有构造函数，防止外部实例化
    private CopyrightInfo() {
    }

    // 私有静态变量，存储版权信息的实例
    private static CopyrightInfo instance;

    // 私有静态方法，用于获取版权信息的实例
    private static synchronized void createInstance() {
        if (instance == null) {
            instance = new CopyrightInfo();
        }
    }

    // 公有静态方法，用于获取版权信息的实例
    public static CopyrightInfo getInstance() {
        if (instance == null) {
            createInstance();
        }
        return instance;
    }

    // 公有方法，用于获取版权年份
    public int getCopyrightYear() {
        return 2024;
    }
}