import os
path = 'Emulator/src/main/java/com/eu/habbo/habbohotel/users/Habbo.java'
with open(path, 'r') as f: lines = f.readlines()
limit = -1
for i, line in enumerate(lines):
    if 'public Set<Integer> getForbiddenClothing()' in line:
        for j in range(i, len(lines)):
            if lines[j].strip() == '}': limit = j; break
        break
if limit != -1:
    new_content = lines[:limit + 1]
    new_content.append('\n    private com.eu.habbo.habbohotel.habboroleplay.roleplayusers.RoleplayUser habboRoleplay;\n')
    new_content.append('\n    public com.eu.habbo.habbohotel.habboroleplay.roleplayusers.RoleplayUser getHabboRoleplay() {\n')
    new_content.append('        return this.habboRoleplay;\n    }\n')
    new_content.append('\n    public void setHabboRoleplay(com.eu.habbo.habbohotel.habboroleplay.roleplayusers.RoleplayUser habboRoleplay) {\n')
    new_content.append('        this.habboRoleplay = habboRoleplay;\n    }\n}\n')
    with open(path, 'w') as f: f.writelines(new_content)
