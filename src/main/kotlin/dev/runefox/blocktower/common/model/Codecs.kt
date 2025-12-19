package dev.runefox.blocktower.common.model

import com.mojang.datafixers.util.Either
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import dev.runefox.blocktower.common.ModRegistries
import dev.runefox.blocktower.common.model.NightOrder.*
import dev.runefox.blocktower.common.util.*
import net.minecraft.core.Holder
import java.util.Optional
import net.minecraft.network.chat.ComponentSerialization.CODEC as ComponentCodec
import net.minecraft.world.item.ItemStack.CODEC as ItemStackCodec

object Codecs {
    val Component = ComponentCodec
    val ItemStack = ItemStackCodec

    val Alignment = enumCodec<Alignment>()
    val RoleType = enumCodec<RoleType>()
    val CharacterType = enumCodec<CharacterType>()

    val String = Codec.STRING as Codec<String>
    val Int = Codec.INT as Codec<Int>

    fun Int(range: IntRange) = Codec.intRange(range.first, range.last) as Codec<Int>

    val Rule = recordCodec<Rule> {
        group(
            Component fieldOf "description" forGetter { description },
            Component fieldOf optional("condition") forNullGetter { condition }
        ).apply(this, ::Rule)
    }

    val Rules = createRules()

    val RuleNightMap = createOptionalNightMap(Rule)

    val Action = recordCodec<Action> {
        group(
            String fieldOf "role" forGetter { role },
            Component fieldOf "description" forGetter { description },
            Component fieldOf optional("condition") forNullGetter { condition }
        ).apply(this, ::Action)
    }

    val Token = recordCodec<Token> {
        group(
            ItemStack fieldOf "icon" forGetter { icon },
            Component fieldOf "name" forGetter { name }
        ).apply(this, ::Token)
    }

    val Role = recordCodec<Role> {
        group(
            RoleType fieldOf "type" forGetter { type },
            Component fieldOf "name" forGetter { name },
            Component fieldOf "description" forGetter { description },
            ItemStack fieldOf "icon" forGetter { icon },
            Component fieldOf optional("lore") forNullGetter { lore },
            Alignment fieldOf optional("alignment_override") forNullGetter { alignmentOverride },
            String fieldOf optional("wiki_url") forNullGetter { wikiUrl },
            Token.List fieldOf optional("tokens", emptyList()) forGetter { tokens },
            RuleNightMap fieldOf optional("night_rule", NightMap(null, null)) forGetter { nightRule },
        ).apply(this, ::Role)
    }

    val RoleHolder = RoleHolder(true)
    val RoleHolderSet = RoleHolderSet(true)

    fun RoleHolder(inline: Boolean) = Role.holder(ModRegistries.ROLE, inline)
    fun RoleHolderSet(inline: Boolean) = Role.holderSet(ModRegistries.ROLE, inline)

    val RoleReference = createRoleReference()

    val Roles = RoleReference.List.xmap(
        { Roles(it) },
        { it.references }
    ).validate {
        it.validate().map { it as Roles }
    } as Codec<Roles>

    val Loric = recordCodec<Loric> {
        group(
            Component fieldOf "name" forGetter { name },
            Component fieldOf "description" forGetter { description },
            ItemStack fieldOf "icon" forGetter { icon },
            Component fieldOf optional("lore") forNullGetter { lore },
            String fieldOf optional("wiki_url") forNullGetter { wikiUrl },
            Token.List fieldOf optional("tokens", emptyList()) forGetter { tokens },
            Rule.List fieldOf optional("night_rule", emptyList()) forGetter { rules },
        ).apply(this, ::Loric)
    }

    val LoricHolder = LoricHolder(false)
    val LoricHolderSet = LoricHolderSet(false)

    fun LoricHolder(inline: Boolean) = Loric.holder(ModRegistries.LORIC, inline)
    fun LoricHolderSet(inline: Boolean) = Loric.holderSet(ModRegistries.LORIC, inline)

    val Fabled = recordCodec<Fabled> {
        group(
            Component fieldOf "name" forGetter { name },
            Component fieldOf "description" forGetter { description },
            ItemStack fieldOf "icon" forGetter { icon },
            Component fieldOf optional("lore") forNullGetter { lore },
            String fieldOf optional("wiki_url") forNullGetter { wikiUrl },
            Token.List fieldOf optional("tokens", emptyList()) forGetter { tokens },
            Rule.List fieldOf optional("night_rule", emptyList()) forGetter { rules },
        ).apply(this, ::Fabled)
    }

    val FabledHolder = FabledHolder(false)
    val FabledHolderSet = FabledHolderSet(false)

    fun FabledHolder(inline: Boolean) = Fabled.holder(ModRegistries.FABLED, inline)
    fun FabledHolderSet(inline: Boolean) = Fabled.holderSet(ModRegistries.FABLED, inline)

    val Distribution = createDistribution()

    val Distributions = Distribution.List.xmap(
        { Distributions(it) },
        { it.distributions }
    ).validate {
        it.validate()
    } as Codec<Distributions>

    val NightOrder = createNightOrderEntry().List.xmap(
        { NightOrder(it) },
        { it.entries }
    ) as Codec<NightOrder>

    val NightOrderNightMap = createNightMap(NightOrder)

    val Script = recordCodec<Script> {
        group(
            Component fieldOf "name" forGetter { name },
            Component fieldOf "description" forGetter { description },
            Roles fieldOf "roles" forGetter { roles },
            NightOrderNightMap fieldOf "night_order" forGetter { nightOrder },
            Distributions fieldOf "distributions" forGetter { distributions },
            Component fieldOf optional("lore") forNullGetter { lore },
            String fieldOf optional("wiki_url") forNullGetter { wikiUrl },
            Token.List fieldOf optional("tokens", emptyList()) forGetter { tokens },
        ).apply(this, ::Script)
    }

    val ScriptHolder = ScriptHolder(false)
    val ScriptHolderSet = ScriptHolderSet(false)

    fun ScriptHolder(inline: Boolean) = Script.holder(ModRegistries.SCRIPT, inline)
    fun ScriptHolderSet(inline: Boolean) = Script.holderSet(ModRegistries.SCRIPT, inline)

    private fun createDistribution(): Codec<Distribution> {
        val range = Int(0..20)

        val array = range.List(4).xmap(
            { Distribution(it[0], it[1], it[2], it[3]) },
            { listOf(it.townsfolk, it.outsiders, it.minions, it.demons) }
        )

        val record = recordCodec<Distribution> {
            group(
                range fieldOf "townsfolk" forGetter { townsfolk },
                range fieldOf "outsiders" forGetter { outsiders },
                range fieldOf "minions" forGetter { minions },
                range fieldOf "demons" forGetter { demons },
            ).apply(this, ::Distribution)
        }

        return (array or record).xmap(
            { it.fold() },
            { Either.left(it) }
        )
    }

    private fun createRoleReference(): Codec<Reference<Holder<Role>>> {
        // Potentially inlined role with explicit alias
        val aliased = recordCodec<Reference<Holder<Role>>> {
            group(
                String fieldOf "alias" forGetter { alias },
                RoleHolder(true) fieldOf "role" forGetter { value }
            ).apply(this, ::Reference)
        }

        // Either a role reference with implied alias, or the above
        return (RoleHolder(false) or aliased).flatXmap(
            {
                it.map(
                    {
                        defaultAlias(it).map { alias ->
                            Reference(alias, it)
                        }
                    },
                    { DataResult.success(it) }
                )
            },
            {
                defaultAlias(it.value).flatMap { alias ->
                    if (alias == it.alias) {
                        DataResult.success(Either.left(it.value))
                    } else {
                        DataResult.success(Either.right(it))
                    }
                }
            }
        )
    }

    private fun defaultAlias(role: Holder<Role>): DataResult<String> {
        val key = role.unwrapKey()
        return if (key.isEmpty) {
            DataResult.error { "Inline role must have a manually defined alias" }
        } else {
            DataResult.success(key.get().identifier().path)
        }
    }

    private fun createRules(): Codec<Rules> {
        val mapping = (String mapping Rule).xmap(
            { Rules(it) },
            { it.byAlias }
        ).validate { it.validate().map { it as Rules } }

        return (Rule or mapping).xmap(
            { it.map({ Rules(it) }, { it }) },
            {
                val single = it.byAlias.entries.singleOrNull()
                if (single?.key == "main") {
                    Either.left(it.main)
                } else {
                    Either.right(it)
                }
            }
        )
    }

    private fun <T : Any> createNightMap(base: Codec<T>): Codec<NightMap<T>> {
        val pair = recordCodec<NightMap<T>> {
            group(
                base fieldOf "first" forGetter { firstNight },
                base fieldOf "other" forGetter { otherNights }
            ).apply(this, ::NightMap)
        }

        return (base or pair).xmap(
            { it.map({ NightMap(it) }, { it }) },
            { (first, other) ->
                if (first == other) {
                    Either.left(first)
                } else {
                    Either.right(NightMap(first, other))
                }
            }
        )
    }

    private fun <T : Any> createOptionalNightMap(base: Codec<T>): Codec<NightMap<T?>> {
        val pair = recordCodec<NightMap<Optional<T>>> {
            group(
                base fieldOf optional("first") forGetter { firstNight },
                base fieldOf optional("other") forGetter { otherNights }
            ).apply(this, ::NightMap)
        }

        return (base or pair).xmap(
            { it.map({ NightMap(it, it) }, { (fst, oth) -> NightMap(fst.orElse(null), oth.orElse(null)) }) },
            { (first, other) ->
                if (first == other && first != null) {
                    Either.left(first)
                } else {
                    Either.right(NightMap(Optional.ofNullable(first), Optional.ofNullable(other)))
                }
            }
        )
    }

    private fun createNightOrderEntry(): Codec<Entry> {
        val main = String.xmap<Main>(
            { Main(it) },
            { it.roleAlias }
        )

        val ignore = (String fieldOf "ignore").codec().xmap<Ignore>(
            { Ignore(it) },
            { it.roleAlias }
        )

        val override = recordCodec<Override> {
            group(
                String fieldOf "role" forGetter { roleAlias },
                Rule fieldOf "rule" forGetter { rule }
            ).apply(this, ::Override)
        }

        return main typeDispatch ignore typeDispatch override
    }
}
