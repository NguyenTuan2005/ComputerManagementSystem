package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortType {
  NAME,
  PRICE,
  RAM,
  MEMORY,
  MONITOR,
  DISK,
  WEIGHT;

  public boolean isName() {
    return this == NAME;
  }

  public boolean isPrice() {
    return this == PRICE;
  }

  public boolean isRam() {
    return this == RAM;
  }

  public boolean isMemory() {
    return this == MEMORY;
  }

  public boolean isMonitor() {
    return this == MONITOR;
  }

  public boolean isDisk() {
    return this == DISK;
  }

  public boolean isWeight() {
    return this == WEIGHT;
  }
}
