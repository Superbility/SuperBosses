package me.superbility.superbosses.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor {
    private StartFightCommand startFightCmd = new StartFightCommand();
    private BossTeleportCommand bossTpCmd = new BossTeleportCommand();
    private GiveToolCommand giveToolCmd = new GiveToolCommand();
    private KillMinionsCommand killMinionsCmd = new KillMinionsCommand();
    private GiveCommand giveCmd = new GiveCommand();
    private SpawnMinionCommand spawnMinionCmd = new SpawnMinionCommand();
    private EndBossFightCommand endBossFightCmd = new EndBossFightCommand();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("superbosses")) {
            if(args.length > 0) {
                String args0 = args[0];

                if(args0.equalsIgnoreCase("spawn")) {
                    startFightCmd.startFight(args, sender);
                    return true;
                }
                if(args0.equalsIgnoreCase("tp")) {
                    bossTpCmd.teleport(args, sender);
                    return true;
                }
                if(args0.equalsIgnoreCase("tool")) {
                    giveToolCmd.give(args, sender);
                    return true;
                }
                if(args0.equalsIgnoreCase("give")) {
                    giveCmd.give(args, sender);
                    return true;
                }
                if(args0.equalsIgnoreCase("killminions")) {
                    killMinionsCmd.kill(args, sender);
                    return true;
                }
                if(args0.equalsIgnoreCase("minion")) {
                    spawnMinionCmd.spawnMinion(args, sender);
                    return true;
                }
                if(args0.equalsIgnoreCase("end")) {
                    endBossFightCmd.end(args, sender);
                    return true;
                }
            }
            //TODO HELP
            return true;
        }
        return false;
    }
}
