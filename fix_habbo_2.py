import sys

filepath = 'Emulator/src/main/java/com/eu/habbo/habbohotel/users/Habbo.java'
with open(filepath, 'r') as f:
    content = f.read()

# Check if methods already exist
if 'getHabboRoleplay' not in content:
    last_brace_index = content.rfind('}')
    if last_brace_index != -1:
        new_methods = """
    private com.eu.habbo.habbohotel.habboroleplay.roleplayusers.RoleplayUser habboRoleplay;

    public com.eu.habbo.habbohotel.habboroleplay.roleplayusers.RoleplayUser getHabboRoleplay() {
        return this.habboRoleplay;
    }

    public void setHabboRoleplay(com.eu.habbo.habbohotel.habboroleplay.roleplayusers.RoleplayUser habboRoleplay) {
        this.habboRoleplay = habboRoleplay;
    }

    public void whisper(String message) {}
    public void talk(String message) {}
    public void shout(String message) {}
    public void whisper(String message, com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles bubble) {}
    public void talk(String message, com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles bubble) {}
    public void shout(String message, com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles bubble) {}

    public void giveCredits(int credits) { this.getHabboInfo().addCredits(credits); }
    public void givePixels(int pixels) { this.getHabboInfo().addPixels(pixels); }
    public void givePoints(int points) { this.getHabboInfo().addCurrency(0, points); }
    public void givePoints(int type, int points) { this.getHabboInfo().addCurrency(type, points); }
    public void alert(String message) {}
    public void alert(String[] message) {}
    public void mute(int duration, boolean seconds) {}
    public void unMute() {}
    public int noobStatus() { return 0; }
    public void addBadge(String badge) {}
    public void respect(Habbo target) {}
"""
        content = content[:last_brace_index] + new_methods + content[last_brace_index:]

with open(filepath, 'w') as f:
    f.write(content)
