package net.dialingspoon.partialhearts;

import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.pipeline.BindGroupLayout;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.BindGroupLayouts;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public final class PartialHearts {
    public static final String MOD_ID = "partialhearts";
    public static RenderPipeline PIPELINE;

    public static void init() {
        BindGroupLayout MATRICES_PROJECTION = BindGroupLayout.builder()
                .withUniform("DynamicTransforms", UniformType.UNIFORM_BUFFER)
                .withUniform("Projection", UniformType.UNIFORM_BUFFER)
                .withUniform("CustomUniform", UniformType.UNIFORM_BUFFER)
                .build();
        
        RenderPipeline pipeline = RenderPipeline.builder(RenderPipelines.GLOBALS_SNIPPET)
                .withBindGroupLayout(MATRICES_PROJECTION)
                .withLocation(Identifier.fromNamespaceAndPath(PartialHearts.MOD_ID, "pipeline/heart_mask"))
                .withVertexShader(Identifier.fromNamespaceAndPath(PartialHearts.MOD_ID, "core/heart_mask"))
                .withFragmentShader(Identifier.fromNamespaceAndPath(PartialHearts.MOD_ID, "core/heart_mask"))

                .withBindGroupLayout(BindGroupLayouts.SAMPLER0)
                .withPrimitiveTopology(PrimitiveTopology.QUADS)
                .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
                .withVertexBinding(0, DefaultVertexFormat.POSITION_TEX_COLOR)
                .build();

        PartialHearts.PIPELINE = RenderPipelines.register(pipeline);
    }
}
