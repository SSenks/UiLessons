package me.kirssenk.UI;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.protocol.packets.interface_.Page;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;


public class UILesson2 extends InteractiveCustomUIPage<UILesson2.AnketDataEvent> {

    public static class AnketDataEvent {
        // Обьявляем переменную соответственно полю ввода
        public String name;

        public static final BuilderCodec<AnketDataEvent> CODEC = BuilderCodec.builder(AnketDataEvent.class, AnketDataEvent::new).append(
                new KeyedCodec<>("@NameFIeld", Codec.STRING),
                (obj, val) -> obj.name = val,
                (obj) -> obj.name
        ).add().build();

    }

    public UILesson2(@NonNullDecl PlayerRef playerRef, @NonNullDecl CustomPageLifetime lifetime) {
        super(playerRef, lifetime, AnketDataEvent.CODEC);
    }

    @Override
    public void build(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl UICommandBuilder uiCommandBuilder, @NonNullDecl UIEventBuilder uiEventBuilder, @NonNullDecl Store<EntityStore> store) {
        uiCommandBuilder.append("lesson2.ui");

        // Слушаем кнопку
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.Activating,
                "#SubmitButton",
                new EventData().append("@NameFIeld", "#InputName.Value")
        );
    }

    @Override
    public void handleDataEvent(Ref<EntityStore> ref, Store<EntityStore> store, AnketDataEvent data) {

        Player player = store.getComponent(ref, Player.getComponentType());

        String name;

        if (data.name != null && !data.name.isEmpty()) {
            name = data.name;
        } else {
            name = "Bad name";
        }

        playerRef.sendMessage(Message.raw("You are registered, " + name));

        player.getPageManager().setPage(ref, store, Page.None);

    }

}
