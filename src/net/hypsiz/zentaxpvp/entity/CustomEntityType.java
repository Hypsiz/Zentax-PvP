/**
 * This file is part of the Zentax PvP Minecraft Server
 * Copyright © 2013 Hai Geydarov <HaiGedav@gmail.com>
 * 
 * Zentax is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation version 3 as published by
 * the Free Software Foundation. You may not use, modify or distribute
 * this program under any other version of the GNU Affero General Public
 * License.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.hypsiz.zentaxpvp.entity;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_7_R1.BiomeBase;
import net.minecraft.server.v1_7_R1.BiomeMeta;
import net.minecraft.server.v1_7_R1.EntityInsentient;
import net.minecraft.server.v1_7_R1.EntityTypes;
import net.minecraft.server.v1_7_R1.EntityVillager;

import org.bukkit.entity.EntityType;

/**
 * @author Hypsiz
 *
 */
public enum CustomEntityType {

	VILLAGER("Villager", 120, EntityType.VILLAGER, EntityVillager.class, CustomEntityVillager.class);
	
	private String name;
    private int id;
    private EntityType entityType;
    private Class<? extends EntityInsentient> nmsClass;
    private Class<? extends EntityInsentient> customClass;
    
    private CustomEntityType(String name, int id, EntityType entityType, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass) {
    	this.name = name;
        this.id = id;
        this.entityType = entityType;
        this.nmsClass = nmsClass;
        this.customClass = customClass;
    }
    
    public String getName() {
    	return this.name;
    }
    
    public int getID() {
    	return this.id;
    }
    
    public EntityType getEntityType() {
        return this.entityType;
    }
 
    public Class<? extends EntityInsentient> getNMSClass() {
        return this.nmsClass;
    }
 
    public Class<? extends EntityInsentient> getCustomClass() {
        return this.customClass;
    }
    
    public static void registerEntities() {
        for (CustomEntityType entity : values())
        	a(entity.getCustomClass(), entity.getName(), entity.getID());
        
        // BiomeBase #biomes became private.
        BiomeBase[] biomes;
        
        try {
            biomes = (BiomeBase[]) getPrivateStatic(BiomeBase.class, "biomes");
        } catch (Exception exc) {
            // Unable to fetch.
            return;
        }
        
        for (BiomeBase biomeBase : biomes) {
            if (biomeBase == null)
                break;
 
            // This changed names from J, K, L and M.
            for (String field : new String[] {
            		"as", "at", "au", "av"
            })
                try {
                    Field list = BiomeBase.class.getDeclaredField(field);
                    list.setAccessible(true);
                    
                    @SuppressWarnings("unchecked")
                    List<BiomeMeta> mobList = (List<BiomeMeta>) list.get(biomeBase);
 
                    // Write in our custom class.
                    for (BiomeMeta meta : mobList)
                        for (CustomEntityType entity : values())
                            if (entity.getNMSClass().equals(meta.b))
                                meta.b = entity.getCustomClass();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
    
    private static Object getPrivateStatic(Class<?> clazz, String f) throws Exception {
        Field field = clazz.getDeclaredField(f);
        field.setAccessible(true);
        
        return field.get(null);
    }
    
    @SuppressWarnings("unchecked")
	private static void a(Class<?> paramClass, String paramString, int paramInt) {
        try {
            ((Map<String, Class<?>>) getPrivateStatic(EntityTypes.class, "c")).put(paramString, paramClass);
            ((Map<Class<?>, String>) getPrivateStatic(EntityTypes.class, "d")).put(paramClass, paramString);
            ((Map<Integer, Class<?>>) getPrivateStatic(EntityTypes.class, "e")).put(Integer.valueOf(paramInt), paramClass);
            ((Map<Class<?>, Integer>) getPrivateStatic(EntityTypes.class, "f")).put(paramClass, Integer.valueOf(paramInt));
            ((Map<String, Integer>) getPrivateStatic(EntityTypes.class, "g")).put(paramString, Integer.valueOf(paramInt));
        } catch (Exception exc) {
            // Unable to register the new class.
        }
    }
}
