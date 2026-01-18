package me.kirssenk.UI;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.protocol.packets.interface_.Page;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;



public class UILesson4 extends InteractiveCustomUIPage<UILesson4.AnketDataEvent> {

    public String nameGlobal = null;
    public String ageGlobal = null;

    public static class AnketDataEvent {
        public String name;
        public String ageStr;

        public static final BuilderCodec<AnketDataEvent> CODEC = BuilderCodec.builder(AnketDataEvent.class, AnketDataEvent::new)
                .append(
                        new KeyedCodec<>("@NameFIeld", Codec.STRING),
                        (obj, val) -> obj.name = val,
                        (obj) -> obj.name
                ).add()
                .append(
                        new KeyedCodec<>("@AgeField", Codec.STRING),
                        (obj, val) -> obj.ageStr = val,
                        (obj) -> obj.ageStr
                ).add()
                .build();

    }

    public UILesson4(String nameGlobal, String ageGlobal, @NonNullDecl PlayerRef playerRef, @NonNullDecl CustomPageLifetime lifetime) {
        super(playerRef, lifetime, AnketDataEvent.CODEC);
        this.nameGlobal = nameGlobal;
        this.ageGlobal = ageGlobal;
    }

    @Override
    public void build(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl UICommandBuilder uiCommandBuilder, @NonNullDecl UIEventBuilder uiEventBuilder, @NonNullDecl Store<EntityStore> store) {

        uiCommandBuilder.append("lesson4.ui");

        // Если есть зарегистрированные данные, отображаем их
        if (nameGlobal != null && ageGlobal != null && !nameGlobal.isEmpty() && !ageGlobal.isEmpty()) {
            uiCommandBuilder.set("#MyLabel.Text", "You are registered, " + nameGlobal + ", age: " + ageGlobal);
        } else {
            uiCommandBuilder.set("#MyLabel.Text", "Registry Anket");
        }

        // Слушаем кнопку
        uiEventBuilder.addEventBinding(
                CustomUIEventBindingType.Activating,
                "#SubmitButton",
                new EventData()
                        .append("@NameFIeld", "#InputName.Value")
                        .append("@AgeField", "#InputAge.Value")
        );
    }

    @Override
    public void handleDataEvent(Ref<EntityStore> ref, Store<EntityStore> store, AnketDataEvent data) {

        Player player = store.getComponent(ref, Player.getComponentType());

        String name;
        Integer age = 0;

        if (data.name != null && !data.name.isEmpty()) {
            name = data.name;
        } else {
            name = "Bad name";
        }

        // Преобразуем строку в число с проверкой
        if (data.ageStr != null && !data.ageStr.isEmpty()) {
            try {
                age = Integer.parseInt(data.ageStr);
            } catch (NumberFormatException e) {
                age = 0;
            }
        } else {
            age = 0;
        }

        player.getPageManager().setPage(ref, store, Page.None);
        player.getPageManager().openCustomPage(ref, store, new UILesson4(name, String.valueOf(age), playerRef, this.lifetime));
    }
}
