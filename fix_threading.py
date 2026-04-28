import os

files = [
    'Emulator/src/main/java/com/eu/habbo/habbohotel/habboroleplay/timers/RoleplayTimer.java',
    'Emulator/src/main/java/com/eu/habbo/habbohotel/habboroleplay/cooldowns/Cooldown.java'
]

for path in files:
    if os.path.exists(path):
        with open(path, 'r') as f:
            content = f.read()

        # In Arcturus 4.1.3 (current version in pom.xml), Emulator.getThreading() returns ThreadPooling
        # ThreadPooling has getService() which returns ScheduledExecutorService.
        # But maybe it's Emulator.getThreading().run(...) or similar.
        # Let's check the error: cannot find symbol method getScheduler()

        # Replacement with getService() which I saw in ThreadPooling.java
        content = content.replace('getScheduler()', 'getService()')

        with open(path, 'w') as f:
            f.write(content)
