package me.kirssenk;

import com.hypixel.hytale.server.core.command.system.CommandRegistry;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import me.kirssenk.commands.FirstLessonCommand;
import me.kirssenk.commands.FourthLessonCommand;
import me.kirssenk.commands.SecondLessonCommand;
import me.kirssenk.commands.ThirdLessonCommand;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class UILessonPlugin extends JavaPlugin {

    public UILessonPlugin(@NonNullDecl JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        // Initialize everything
        CommandRegistry commandRegistry = this.getCommandRegistry();
        commandRegistry.registerCommand(new FirstLessonCommand("first", "first UI command"));
        commandRegistry.registerCommand(new SecondLessonCommand("second", "second UI command"));
        commandRegistry.registerCommand(new ThirdLessonCommand("third", "third UI command"));
        commandRegistry.registerCommand(new FourthLessonCommand("fourth", "fourth UI command"));
    }
}