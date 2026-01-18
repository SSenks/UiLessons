package me.kirssenk.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.kirssenk.UI.UILesson4;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class FourthLessonCommand extends AbstractPlayerCommand {


    public FourthLessonCommand(@NonNullDecl String name, @NonNullDecl String description) {
        super(name, description);
    }

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {

        Player player = (Player) commandContext.sender();

        player.getPageManager().openCustomPage(ref, store, new UILesson4(null, null, playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction));
    }
}
