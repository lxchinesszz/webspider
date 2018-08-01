package org.spider.config;

import org.spider.util.Console;

/**
 * @author liuxin
 * @version Id: StaticBanner.java, v 0.1 2018/7/27 下午8:47
 */
public class StaticBanner implements Banner {
    private String blocks = " .----------------. .----------------. .----------------. .----------------. .----------------. .----------------. .----------------. .----------------. .----------------. \n" +
            "| .--------------. | .--------------. | .--------------. | .--------------. | .--------------. | .--------------. | .--------------. | .--------------. | .--------------. |\n" +
            "| | _____  _____ | | |  _________   | | |   ______     | | |    _______   | | |   ______     | | |     _____    | | |  ________    | | |  _________   | | |  _______     | |\n" +
            "| ||_   _||_   _|| | | |_   ___  |  | | |  |_   _ \\    | | |   /  ___  |  | | |  |_   __ \\   | | |    |_   _|   | | | |_   ___ `.  | | | |_   ___  |  | | | |_   __ \\    | |\n" +
            "| |  | | /\\ | |  | | |   | |_  \\_|  | | |    | |_) |   | | |  |  (__ \\_|  | | |    | |__) |  | | |      | |     | | |   | |   `. \\ | | |   | |_  \\_|  | | |   | |__) |   | |\n" +
            "| |  | |/  \\| |  | | |   |  _|  _   | | |    |  __'.   | | |   '.___`-.   | | |    |  ___/   | | |      | |     | | |   | |    | | | | |   |  _|  _   | | |   |  __ /    | |\n" +
            "| |  |   /\\   |  | | |  _| |___/ |  | | |   _| |__) |  | | |  |`\\____) |  | | |   _| |_      | | |     _| |_    | | |  _| |___.' / | | |  _| |___/ |  | | |  _| |  \\ \\_  | |\n" +
            "| |  |__/  \\__|  | | | |_________|  | | |  |_______/   | | |  |_______.'  | | |  |_____|     | | |    |_____|   | | | |________.'  | | | |_________|  | | | |____| |___| | |\n" +
            "| |              | | |              | | |              | | |              | | |              | | |              | | |              | | |              | | |              | |\n" +
            "| '--------------' | '--------------' | '--------------' | '--------------' | '--------------' | '--------------' | '--------------' | '--------------' | '--------------' |\n" +
            " '----------------' '----------------' '----------------' '----------------' '----------------' '----------------' '----------------' '----------------' '----------------' ";

    private String bigMoneySe = " __       __            __         ______             __        __                               \n" +
            "|  \\  _  |  \\          |  \\       /      \\           |  \\      |  \\                              \n" +
            "| $$ / \\ | $$  ______  | $$____  |  $$$$$$\\  ______   \\$$  ____| $$  ______    ______    ______  \n" +
            "| $$/  $\\| $$ /      \\ | $$    \\ | $$___\\$$ /      \\ |  \\ /      $$ /      \\  /      \\  /      \\ \n" +
            "| $$  $$$\\ $$|  $$$$$$\\| $$$$$$$\\ \\$$    \\ |  $$$$$$\\| $$|  $$$$$$$|  $$$$$$\\|  $$$$$$\\|  $$$$$$\\\n" +
            "| $$ $$\\$$\\$$| $$    $$| $$  | $$ _\\$$$$$$\\| $$  | $$| $$| $$  | $$| $$    $$| $$   \\$$| $$    $$\n" +
            "| $$$$  \\$$$$| $$$$$$$$| $$__/ $$|  \\__| $$| $$__/ $$| $$| $$__| $$| $$$$$$$$| $$      | $$$$$$$$\n" +
            "| $$$    \\$$$ \\$$     \\| $$    $$ \\$$    $$| $$    $$| $$ \\$$    $$ \\$$     \\| $$       \\$$     \\\n" +
            " \\$$      \\$$  \\$$$$$$$ \\$$$$$$$   \\$$$$$$ | $$$$$$$  \\$$  \\$$$$$$$  \\$$$$$$$ \\$$        \\$$$$$$$\n" +
            "                                           | $$                                                  \n" +
            "                                           | $$                                                  \n" +
            "                                            \\$$  ";


    @Override
    public String printBanner() {
        Console console = Console.create();
        console.green(bigMoneySe);
        console.cyan("[WebSpider 启动成功.... ]");
        return null;
    }
}
