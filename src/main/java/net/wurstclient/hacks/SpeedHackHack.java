/*
 * Copyright (c) 2014-2024 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.hacks;

import net.minecraft.util.math.Vec3d;
import net.wurstclient.Category;
import net.wurstclient.SearchTags;
import net.wurstclient.events.UpdateListener;
import net.wurstclient.hack.Hack;
import net.wurstclient.settings.CheckboxSetting;
import net.wurstclient.settings.SliderSetting;

@SearchTags({"speed hack"})
public final class SpeedHackHack extends Hack implements UpdateListener
{
	private final SliderSetting speed =
			new SliderSetting("Speed", 0.2, 0.1, 20, 0.1, SliderSetting.ValueDisplay.DECIMAL);
	private final CheckboxSetting jump =
			new CheckboxSetting(
					"Jump?",
					"Jump on ground.",
					true
			);

	public SpeedHackHack()
	{
		super("SpeedHack");
		setCategory(Category.MOVEMENT);
		addSetting(speed);
		addSetting(jump);

	}
	
	@Override
	public void onEnable()
	{
		EVENTS.add(UpdateListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		EVENTS.remove(UpdateListener.class, this);
	}
	
	@Override
	public void onUpdate()
	{
		MC.player.setVelocity(0,MC.player.getVelocity().y,0);
		if (MC.player.isOnGround()) {
			if (jump.isChecked() && (MC.player.sidewaysSpeed !=0 || MC.player.forwardSpeed !=0)) {
				MC.player.jump();
			}
			MC.player.updateVelocity(speed.getValueF(),new Vec3d(MC.player.sidewaysSpeed,0,MC.player.forwardSpeed));
		} else {
			MC.player.updateVelocity(speed.getValueF()*2,new Vec3d(MC.player.sidewaysSpeed,0,MC.player.forwardSpeed));
		}

	}
}
