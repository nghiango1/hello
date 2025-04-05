def take_magic_damage(health, resist, amp, spell_power):
    new_health = health
    max_damage = spell_power * amp
    actual_damage = max_damage - resist
    new_health -= actual_damage
    return new_health
