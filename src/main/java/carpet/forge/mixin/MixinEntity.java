package carpet.forge.mixin;

import carpet.forge.helper.BlockRotator;
import carpet.forge.utils.mixininterfaces.IMixinEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinEntity implements IMixinEntity
{

    @Shadow
    public float rotationYaw;
    @Shadow
    private int fire;

    public int getFire()
    {
        return this.fire;
    }

    public String cm_name()
    {
        return "Other Entity";
    }

    @Inject(method = "getHorizontalFacing", at = @At(value = "RETURN", shift = At.Shift.BEFORE), cancellable = true)
    private void flippinEligibility(CallbackInfoReturnable<EnumFacing> cir)
    {
        if (BlockRotator.flippinEligibility((Entity) (Object) this))
            cir.setReturnValue(EnumFacing.byHorizontalIndex(MathHelper.floor((double) (this.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3).getOpposite());
    }

}
