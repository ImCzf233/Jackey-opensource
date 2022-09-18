package net.ccbluex.liquidbounce.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockWall;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/AStarCustomPathFinder.class */
public class AStarCustomPathFinder {
    private Vec3 startVec3;
    private Vec3 endVec3;
    private ArrayList<Vec3> path = new ArrayList<>();
    private ArrayList<Hub> hubs = new ArrayList<>();
    private ArrayList<Hub> hubsToWork = new ArrayList<>();
    private double minDistanceSquared = 9.0d;
    private boolean nearest = true;
    private static final Vec3[] flatCardinalDirections = {new Vec3(1.0d, 0.0d, 0.0d), new Vec3(-1.0d, 0.0d, 0.0d), new Vec3(0.0d, 0.0d, 1.0d), new Vec3(0.0d, 0.0d, -1.0d)};

    public AStarCustomPathFinder(Vec3 startVec3, Vec3 endVec3) {
        this.startVec3 = BlockUtils.floorVec3(startVec3.func_72441_c(0.0d, 0.0d, 0.0d));
        this.endVec3 = BlockUtils.floorVec3(endVec3.func_72441_c(0.0d, 0.0d, 0.0d));
    }

    public ArrayList<Vec3> getPath() {
        return this.path;
    }

    public void compute() {
        compute(1000, 4);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x00f8, code lost:
        r0 = net.ccbluex.liquidbounce.utils.block.BlockUtils.floorVec3(r0.getLoc().func_72441_c(0.0d, 1.0d, 0.0d));
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x010e, code lost:
        if (checkPositionValidity(r0, false) == false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x011a, code lost:
        if (addHub(r0, r0, 0.0d) == false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0120, code lost:
        r0 = net.ccbluex.liquidbounce.utils.block.BlockUtils.floorVec3(r0.getLoc().func_72441_c(0.0d, -1.0d, 0.0d));
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0138, code lost:
        if (checkPositionValidity(r0, false) == false) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0144, code lost:
        if (addHub(r0, r0, 0.0d) == false) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x014d, code lost:
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void compute(int r15, int r16) {
        /*
            Method dump skipped, instructions count: 380
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.utils.AStarCustomPathFinder.compute(int, int):void");
    }

    public static boolean checkPositionValidity(Vec3 loc, boolean checkGround) {
        return checkPositionValidity((int) loc.field_72450_a, (int) loc.field_72448_b, (int) loc.field_72449_c, checkGround);
    }

    public static boolean checkPositionValidity(int x, int y, int z, boolean checkGround) {
        BlockPos block1 = new BlockPos(x, y, z);
        BlockPos block2 = new BlockPos(x, y + 1, z);
        BlockPos block3 = new BlockPos(x, y - 1, z);
        return !isBlockSolid(block1) && !isBlockSolid(block2) && (isBlockSolid(block3) || !checkGround) && isSafeToWalkOn(block3);
    }

    private static boolean isBlockSolid(BlockPos blockPos) {
        Block block = BlockUtils.getBlock(blockPos);
        if (block == null) {
            return false;
        }
        return block.func_149730_j() || (block instanceof BlockSlab) || (block instanceof BlockStairs) || (block instanceof BlockCactus) || (block instanceof BlockChest) || (block instanceof BlockEnderChest) || (block instanceof BlockSkull) || (block instanceof BlockPane) || (block instanceof BlockFence) || (block instanceof BlockWall) || (block instanceof BlockGlass) || (block instanceof BlockPistonBase) || (block instanceof BlockPistonExtension) || (block instanceof BlockPistonMoving) || (block instanceof BlockStainedGlass) || (block instanceof BlockTrapDoor);
    }

    private static boolean isSafeToWalkOn(BlockPos blockPos) {
        Block block = BlockUtils.getBlock(blockPos);
        return block != null && !(block instanceof BlockFence) && !(block instanceof BlockWall);
    }

    public Hub isHubExisting(Vec3 loc) {
        Iterator<Hub> it = this.hubs.iterator();
        while (it.hasNext()) {
            Hub hub = it.next();
            if (hub.getLoc().field_72450_a == loc.field_72450_a && hub.getLoc().field_72448_b == loc.field_72448_b && hub.getLoc().field_72449_c == loc.field_72449_c) {
                return hub;
            }
        }
        Iterator<Hub> it2 = this.hubsToWork.iterator();
        while (it2.hasNext()) {
            Hub hub2 = it2.next();
            if (hub2.getLoc().field_72450_a == loc.field_72450_a && hub2.getLoc().field_72448_b == loc.field_72448_b && hub2.getLoc().field_72449_c == loc.field_72449_c) {
                return hub2;
            }
        }
        return null;
    }

    public boolean addHub(Hub parent, Vec3 loc, double cost) {
        Hub existingHub = isHubExisting(loc);
        double totalCost = cost;
        if (parent != null) {
            totalCost += parent.getTotalCost();
        }
        if (existingHub == null) {
            if ((loc.field_72450_a == this.endVec3.field_72450_a && loc.field_72448_b == this.endVec3.field_72448_b && loc.field_72449_c == this.endVec3.field_72449_c) || (this.minDistanceSquared != 0.0d && loc.func_72436_e(this.endVec3) <= this.minDistanceSquared)) {
                this.path.clear();
                this.path = parent.getPath();
                this.path.add(loc);
                return true;
            }
            ArrayList<Vec3> path = new ArrayList<>(parent.getPath());
            path.add(loc);
            this.hubsToWork.add(new Hub(loc, parent, path, loc.func_72436_e(this.endVec3), cost, totalCost));
            return false;
        } else if (existingHub.getCost() > cost) {
            ArrayList<Vec3> path2 = new ArrayList<>(parent.getPath());
            path2.add(loc);
            existingHub.setLoc(loc);
            existingHub.setParent(parent);
            existingHub.setPath(path2);
            existingHub.setSquareDistanceToFromTarget(loc.func_72436_e(this.endVec3));
            existingHub.setCost(cost);
            existingHub.setTotalCost(totalCost);
            return false;
        } else {
            return false;
        }
    }

    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/AStarCustomPathFinder$Hub.class */
    public class Hub {
        private Vec3 loc;
        private Hub parent;
        private ArrayList<Vec3> path;
        private double squareDistanceToFromTarget;
        private double cost;
        private double totalCost;

        public Hub(Vec3 loc, Hub parent, ArrayList<Vec3> path, double squareDistanceToFromTarget, double cost, double totalCost) {
            AStarCustomPathFinder.this = r5;
            this.loc = null;
            this.parent = null;
            this.loc = loc;
            this.parent = parent;
            this.path = path;
            this.squareDistanceToFromTarget = squareDistanceToFromTarget;
            this.cost = cost;
            this.totalCost = totalCost;
        }

        public Vec3 getLoc() {
            return this.loc;
        }

        public Hub getParent() {
            return this.parent;
        }

        public ArrayList<Vec3> getPath() {
            return this.path;
        }

        public double getSquareDistanceToFromTarget() {
            return this.squareDistanceToFromTarget;
        }

        public double getCost() {
            return this.cost;
        }

        public void setLoc(Vec3 loc) {
            this.loc = loc;
        }

        public void setParent(Hub parent) {
            this.parent = parent;
        }

        public void setPath(ArrayList<Vec3> path) {
            this.path = path;
        }

        public void setSquareDistanceToFromTarget(double squareDistanceToFromTarget) {
            this.squareDistanceToFromTarget = squareDistanceToFromTarget;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public double getTotalCost() {
            return this.totalCost;
        }

        public void setTotalCost(double totalCost) {
            this.totalCost = totalCost;
        }
    }

    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/AStarCustomPathFinder$CompareHub.class */
    public class CompareHub implements Comparator<Hub> {
        public CompareHub() {
            AStarCustomPathFinder.this = this$0;
        }

        public int compare(Hub o1, Hub o2) {
            return (int) ((o1.getSquareDistanceToFromTarget() + o1.getTotalCost()) - (o2.getSquareDistanceToFromTarget() + o2.getTotalCost()));
        }
    }
}
