//Enum para o collection item
public enum ConditionEnum {
MINT("Mint (M)", "Perfeito, como novo."),
NEAR_MINT("Near Mint (NM)", "Quase perfeito."),
VERY_GOOD_PLUS("Very Good Plus (VG+)", "Alguns sinais de uso, mas toca bem."),
VERY_GOOD("Very Good (VG)", "Ruídos de fundo, mas não pula."),
GOOD("Good (G)", "Toca sem pular, mas com muito ruído."),
POOR("Poor (P)", "Quebrado ou pula muito.");

private final String label;
private final String description;

ConditionEnum(String label, String description) {
this.label = label;
this.description = description;
}

public String getLabel() { return label; }
}
