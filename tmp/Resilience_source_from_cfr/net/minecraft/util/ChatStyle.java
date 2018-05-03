/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 */
package net.minecraft.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class ChatStyle {
    private ChatStyle parentStyle;
    private EnumChatFormatting color;
    private Boolean bold;
    private Boolean italic;
    private Boolean underlined;
    private Boolean strikethrough;
    private Boolean obfuscated;
    private ClickEvent chatClickEvent;
    private HoverEvent chatHoverEvent;
    private static final ChatStyle rootStyle = new ChatStyle(){
        private static final String __OBFID = "CL_00001267";

        @Override
        public EnumChatFormatting getColor() {
            return null;
        }

        @Override
        public boolean getBold() {
            return false;
        }

        @Override
        public boolean getItalic() {
            return false;
        }

        @Override
        public boolean getStrikethrough() {
            return false;
        }

        @Override
        public boolean getUnderlined() {
            return false;
        }

        @Override
        public boolean getObfuscated() {
            return false;
        }

        @Override
        public ClickEvent getChatClickEvent() {
            return null;
        }

        @Override
        public HoverEvent getChatHoverEvent() {
            return null;
        }

        @Override
        public ChatStyle setColor(EnumChatFormatting p_150238_1_) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ChatStyle setBold(Boolean p_150227_1_) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ChatStyle setItalic(Boolean p_150217_1_) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ChatStyle setStrikethrough(Boolean p_150225_1_) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ChatStyle setUnderlined(Boolean p_150228_1_) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ChatStyle setObfuscated(Boolean p_150237_1_) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ChatStyle setChatClickEvent(ClickEvent p_150241_1_) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ChatStyle setChatHoverEvent(HoverEvent p_150209_1_) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ChatStyle setParentStyle(ChatStyle p_150221_1_) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return "Style.ROOT";
        }

        @Override
        public ChatStyle createShallowCopy() {
            return this;
        }

        @Override
        public ChatStyle createDeepCopy() {
            return this;
        }

        @Override
        public String getFormattingCode() {
            return "";
        }
    };
    private static final String __OBFID = "CL_00001266";

    public EnumChatFormatting getColor() {
        return this.color == null ? this.getParent().getColor() : this.color;
    }

    public boolean getBold() {
        return this.bold == null ? this.getParent().getBold() : this.bold.booleanValue();
    }

    public boolean getItalic() {
        return this.italic == null ? this.getParent().getItalic() : this.italic.booleanValue();
    }

    public boolean getStrikethrough() {
        return this.strikethrough == null ? this.getParent().getStrikethrough() : this.strikethrough.booleanValue();
    }

    public boolean getUnderlined() {
        return this.underlined == null ? this.getParent().getUnderlined() : this.underlined.booleanValue();
    }

    public boolean getObfuscated() {
        return this.obfuscated == null ? this.getParent().getObfuscated() : this.obfuscated.booleanValue();
    }

    public boolean isEmpty() {
        if (this.bold == null && this.italic == null && this.strikethrough == null && this.underlined == null && this.obfuscated == null && this.color == null && this.chatClickEvent == null && this.chatHoverEvent == null) {
            return true;
        }
        return false;
    }

    public ClickEvent getChatClickEvent() {
        return this.chatClickEvent == null ? this.getParent().getChatClickEvent() : this.chatClickEvent;
    }

    public HoverEvent getChatHoverEvent() {
        return this.chatHoverEvent == null ? this.getParent().getChatHoverEvent() : this.chatHoverEvent;
    }

    public ChatStyle setColor(EnumChatFormatting p_150238_1_) {
        this.color = p_150238_1_;
        return this;
    }

    public ChatStyle setBold(Boolean p_150227_1_) {
        this.bold = p_150227_1_;
        return this;
    }

    public ChatStyle setItalic(Boolean p_150217_1_) {
        this.italic = p_150217_1_;
        return this;
    }

    public ChatStyle setStrikethrough(Boolean p_150225_1_) {
        this.strikethrough = p_150225_1_;
        return this;
    }

    public ChatStyle setUnderlined(Boolean p_150228_1_) {
        this.underlined = p_150228_1_;
        return this;
    }

    public ChatStyle setObfuscated(Boolean p_150237_1_) {
        this.obfuscated = p_150237_1_;
        return this;
    }

    public ChatStyle setChatClickEvent(ClickEvent p_150241_1_) {
        this.chatClickEvent = p_150241_1_;
        return this;
    }

    public ChatStyle setChatHoverEvent(HoverEvent p_150209_1_) {
        this.chatHoverEvent = p_150209_1_;
        return this;
    }

    public ChatStyle setParentStyle(ChatStyle p_150221_1_) {
        this.parentStyle = p_150221_1_;
        return this;
    }

    public String getFormattingCode() {
        if (this.isEmpty()) {
            return this.parentStyle != null ? this.parentStyle.getFormattingCode() : "";
        }
        StringBuilder var1 = new StringBuilder();
        if (this.getColor() != null) {
            var1.append((Object)this.getColor());
        }
        if (this.getBold()) {
            var1.append((Object)EnumChatFormatting.BOLD);
        }
        if (this.getItalic()) {
            var1.append((Object)EnumChatFormatting.ITALIC);
        }
        if (this.getUnderlined()) {
            var1.append((Object)EnumChatFormatting.UNDERLINE);
        }
        if (this.getObfuscated()) {
            var1.append((Object)EnumChatFormatting.OBFUSCATED);
        }
        if (this.getStrikethrough()) {
            var1.append((Object)EnumChatFormatting.STRIKETHROUGH);
        }
        return var1.toString();
    }

    private ChatStyle getParent() {
        return this.parentStyle == null ? rootStyle : this.parentStyle;
    }

    public String toString() {
        return "Style{hasParent=" + (this.parentStyle != null) + ", color=" + (Object)((Object)this.color) + ", bold=" + this.bold + ", italic=" + this.italic + ", underlined=" + this.underlined + ", obfuscated=" + this.obfuscated + ", clickEvent=" + this.getChatClickEvent() + ", hoverEvent=" + this.getChatHoverEvent() + '}';
    }

    public boolean equals(Object par1Obj) {
        block6 : {
            ChatStyle var2;
            block8 : {
                block7 : {
                    if (this == par1Obj) {
                        return true;
                    }
                    if (!(par1Obj instanceof ChatStyle)) {
                        return false;
                    }
                    var2 = (ChatStyle)par1Obj;
                    if (this.getBold() != var2.getBold() || this.getColor() != var2.getColor() || this.getItalic() != var2.getItalic() || this.getObfuscated() != var2.getObfuscated() || this.getStrikethrough() != var2.getStrikethrough() || this.getUnderlined() != var2.getUnderlined()) break block6;
                    if (this.getChatClickEvent() == null) break block7;
                    if (this.getChatClickEvent().equals(var2.getChatClickEvent())) break block8;
                    break block6;
                }
                if (var2.getChatClickEvent() != null) break block6;
            }
            if (!(this.getChatHoverEvent() != null ? !this.getChatHoverEvent().equals(var2.getChatHoverEvent()) : var2.getChatHoverEvent() != null)) {
                boolean var10000 = true;
                return var10000;
            }
        }
        boolean var10000 = false;
        return var10000;
    }

    public int hashCode() {
        int var1 = this.color.hashCode();
        var1 = 31 * var1 + this.bold.hashCode();
        var1 = 31 * var1 + this.italic.hashCode();
        var1 = 31 * var1 + this.underlined.hashCode();
        var1 = 31 * var1 + this.strikethrough.hashCode();
        var1 = 31 * var1 + this.obfuscated.hashCode();
        var1 = 31 * var1 + this.chatClickEvent.hashCode();
        var1 = 31 * var1 + this.chatHoverEvent.hashCode();
        return var1;
    }

    public ChatStyle createShallowCopy() {
        ChatStyle var1 = new ChatStyle();
        var1.bold = this.bold;
        var1.italic = this.italic;
        var1.strikethrough = this.strikethrough;
        var1.underlined = this.underlined;
        var1.obfuscated = this.obfuscated;
        var1.color = this.color;
        var1.chatClickEvent = this.chatClickEvent;
        var1.chatHoverEvent = this.chatHoverEvent;
        var1.parentStyle = this.parentStyle;
        return var1;
    }

    public ChatStyle createDeepCopy() {
        ChatStyle var1 = new ChatStyle();
        var1.setBold(this.getBold());
        var1.setItalic(this.getItalic());
        var1.setStrikethrough(this.getStrikethrough());
        var1.setUnderlined(this.getUnderlined());
        var1.setObfuscated(this.getObfuscated());
        var1.setColor(this.getColor());
        var1.setChatClickEvent(this.getChatClickEvent());
        var1.setChatHoverEvent(this.getChatHoverEvent());
        return var1;
    }

    static /* synthetic */ void access$0(ChatStyle chatStyle, Boolean bl) {
        chatStyle.bold = bl;
    }

    static /* synthetic */ void access$1(ChatStyle chatStyle, Boolean bl) {
        chatStyle.italic = bl;
    }

    static /* synthetic */ void access$2(ChatStyle chatStyle, Boolean bl) {
        chatStyle.underlined = bl;
    }

    static /* synthetic */ void access$3(ChatStyle chatStyle, Boolean bl) {
        chatStyle.strikethrough = bl;
    }

    static /* synthetic */ void access$4(ChatStyle chatStyle, Boolean bl) {
        chatStyle.obfuscated = bl;
    }

    static /* synthetic */ void access$5(ChatStyle chatStyle, EnumChatFormatting enumChatFormatting) {
        chatStyle.color = enumChatFormatting;
    }

    static /* synthetic */ void access$6(ChatStyle chatStyle, ClickEvent clickEvent) {
        chatStyle.chatClickEvent = clickEvent;
    }

    static /* synthetic */ void access$7(ChatStyle chatStyle, HoverEvent hoverEvent) {
        chatStyle.chatHoverEvent = hoverEvent;
    }

    public static class Serializer
    implements JsonDeserializer,
    JsonSerializer {
        private static final String __OBFID = "CL_00001268";

        public ChatStyle deserialize(JsonElement p_150204_1_, Type p_150204_2_, JsonDeserializationContext p_150204_3_) {
            if (p_150204_1_.isJsonObject()) {
                JsonObject var6;
                ChatStyle var4 = new ChatStyle();
                JsonObject var5 = p_150204_1_.getAsJsonObject();
                if (var5.has("bold")) {
                    ChatStyle.access$0(var4, var5.get("bold").getAsBoolean());
                }
                if (var5.has("italic")) {
                    ChatStyle.access$1(var4, var5.get("italic").getAsBoolean());
                }
                if (var5.has("underlined")) {
                    ChatStyle.access$2(var4, var5.get("underlined").getAsBoolean());
                }
                if (var5.has("strikethrough")) {
                    ChatStyle.access$3(var4, var5.get("strikethrough").getAsBoolean());
                }
                if (var5.has("obfuscated")) {
                    ChatStyle.access$4(var4, var5.get("obfuscated").getAsBoolean());
                }
                if (var5.has("color")) {
                    ChatStyle.access$5(var4, (EnumChatFormatting)((Object)p_150204_3_.deserialize(var5.get("color"), EnumChatFormatting.class)));
                }
                if (var5.has("clickEvent")) {
                    var6 = var5.getAsJsonObject("clickEvent");
                    ClickEvent.Action var7 = ClickEvent.Action.getValueByCanonicalName(var6.getAsJsonPrimitive("action").getAsString());
                    String var8 = var6.getAsJsonPrimitive("value").getAsString();
                    if (var7 != null && var8 != null && var7.shouldAllowInChat()) {
                        ChatStyle.access$6(var4, new ClickEvent(var7, var8));
                    }
                }
                if (var5.has("hoverEvent")) {
                    var6 = var5.getAsJsonObject("hoverEvent");
                    HoverEvent.Action var9 = HoverEvent.Action.getValueByCanonicalName(var6.getAsJsonPrimitive("action").getAsString());
                    IChatComponent var10 = (IChatComponent)p_150204_3_.deserialize(var6.get("value"), IChatComponent.class);
                    if (var9 != null && var10 != null && var9.shouldAllowInChat()) {
                        ChatStyle.access$7(var4, new HoverEvent(var9, var10));
                    }
                }
                return var4;
            }
            return null;
        }

        public JsonElement serialize(ChatStyle p_150203_1_, Type p_150203_2_, JsonSerializationContext p_150203_3_) {
            JsonObject var5;
            if (p_150203_1_.isEmpty()) {
                return null;
            }
            JsonObject var4 = new JsonObject();
            if (p_150203_1_.bold != null) {
                var4.addProperty("bold", p_150203_1_.bold);
            }
            if (p_150203_1_.italic != null) {
                var4.addProperty("italic", p_150203_1_.italic);
            }
            if (p_150203_1_.underlined != null) {
                var4.addProperty("underlined", p_150203_1_.underlined);
            }
            if (p_150203_1_.strikethrough != null) {
                var4.addProperty("strikethrough", p_150203_1_.strikethrough);
            }
            if (p_150203_1_.obfuscated != null) {
                var4.addProperty("obfuscated", p_150203_1_.obfuscated);
            }
            if (p_150203_1_.color != null) {
                var4.add("color", p_150203_3_.serialize((Object)p_150203_1_.color));
            }
            if (p_150203_1_.chatClickEvent != null) {
                var5 = new JsonObject();
                var5.addProperty("action", p_150203_1_.chatClickEvent.getAction().getCanonicalName());
                var5.addProperty("value", p_150203_1_.chatClickEvent.getValue());
                var4.add("clickEvent", (JsonElement)var5);
            }
            if (p_150203_1_.chatHoverEvent != null) {
                var5 = new JsonObject();
                var5.addProperty("action", p_150203_1_.chatHoverEvent.getAction().getCanonicalName());
                var5.add("value", p_150203_3_.serialize((Object)p_150203_1_.chatHoverEvent.getValue()));
                var4.add("hoverEvent", (JsonElement)var5);
            }
            return var4;
        }

        public JsonElement serialize(Object par1Obj, Type par2Type, JsonSerializationContext par3JsonSerializationContext) {
            return this.serialize((ChatStyle)par1Obj, par2Type, par3JsonSerializationContext);
        }
    }

}

