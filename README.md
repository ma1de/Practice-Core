# ⚡ Practice
Practice core made for Minecraft

> [!IMPORTANT]
> This wasn't meant for a production server.<br> 
> If you decide to use it on your server then, please, bug test and edit the source code if needed.<br>
> This code wasn't tested

# ⭐ Features
Data is being saved in both MongoDB and JSON files.
<br>
Queue system (Ranked and Unranked)
<br>
[TODO] GUI for managing arenas, customizable kits, etc.
<br>
[TODO] Kohi tournaments, events

**Easy way to implement kit logic (ex. Bridges)**

```java
import me.ma1de.practice.match.event.impl.MatchEndEvent;
import me.ma1de.practice.match.event.impl.MatchPreStartEvent;
import me.ma1de.practice.match.event.impl.MatchStartEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

public class BridgeLogicListener implements Listener {
    @EventHandler
    public void onMatchPreStart(MatchPreStartEvent event) { // Called when the countdown starts, can be cancelled
        // Do something
    }

    @EventHandler
    public void onMatchStart(MatchStartEvent event) { // Called when the countdown ends, ignores org.bukkit.event.Event#isCancelled
        // Do something
    }

    @EventHandler
    public void onMatchEnd(MatchEndEvent event) { 
        // Do something
    }
}
```

```java
import me.ma1de.practice.Practice;
import me.ma1de.example.listener.BridgeLogicListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        Practice.getInstance().registerCustomListener(new BridgeLogicListener());
    }
}
```
or just modify the source code and call the `Practice#registerCustomListener` method in `Practice#onEnable`

# ✅ Requirements
> [!NOTE]
> This list is gonna be updated

MongoDB <br>
Spigot API (1.8.8) <br>
Java 1.8<br>
Maven<br>
(Not required, but recommended) Linux

# 👨 Contribution
Make a PR and I will review it.

> [!NOTE]
> I am aware that the code might not be the best<br>
> This wasn't meant to be a serious project
