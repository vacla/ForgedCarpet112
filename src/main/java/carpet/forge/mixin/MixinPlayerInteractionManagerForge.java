package carpet.forge.mixin;

import carpet.forge.helper.BlockRotator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerInteractionManager.class)
public abstract class MixinPlayerInteractionManagerForge
{
    @Inject(method = "processRightClickBlock", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/block/state/IBlockState;getBlock()Lnet/minecraft/block/Block;",
            ordinal = 1, shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
    private void checkFlipCactus(EntityPlayer player, World worldIn, ItemStack stack, EnumHand hand, BlockPos pos,
            EnumFacing facing, float hitX, float hitY, float hitZ, CallbackInfoReturnable<EnumActionResult> cir,
            double reachDist, PlayerInteractEvent.RightClickBlock event, EnumActionResult result, boolean bypass,
            IBlockState iblockstate)
    {
        boolean flipped = BlockRotator.flipBlockWithCactus(worldIn, pos, iblockstate, player, hand, facing, hitX, hitY, hitZ);
        if (flipped) {
            cir.setReturnValue(EnumActionResult.PASS);
        }
    }
}
