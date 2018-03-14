package me.tcpackfrequency.orbitalmc.managers;

import me.tcpackfrequency.orbitalmc.profile.Profile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileManager {

    private Map<UUID, Profile> profileMap = new HashMap<>();

    public Profile getOrCreateProfile(UUID u){
        return profileMap.computeIfAbsent(u, ignored -> new Profile(u));
    }

    public void RemoveProfile(UUID u){
        profileMap.remove(u);
    }


}
