package rosie.green.rosewood.datagen.helpers;

import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

@NullMarked
public class TagBuilder<T> implements TagAppender<T> {
    private final TagAppender<T> wrapped;
    private final Function<T, ResourceKey<T>> elementNamer;

    public TagBuilder(TagAppender<T> wrapped, Function<T, ResourceKey<T>> elementNamer) {
        this.wrapped = wrapped;
        this.elementNamer = elementNamer;
    }

    public TagBuilder<T> add(T element) {
        this.wrapped.add(this.elementNamer.apply(element));

        return this;
    }

    @Override
    public TagBuilder<T> add(ResourceKey<T> element) {
        wrapped.add(element);

        return this;
    }

    @Override
    public TagBuilder<T> addOptional(ResourceKey<T> element) {
        wrapped.addOptional(element);

        return this;
    }

    @Override
    public TagBuilder<T> addTag(TagKey<T> tag) {
        wrapped.addTag(tag);

        return this;
    }

    @Override
    public TagBuilder<T> addOptionalTag(TagKey<T> tag) {
        wrapped.addOptionalTag(tag);

        return this;
    }
}
