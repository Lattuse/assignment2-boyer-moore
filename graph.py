import pandas as pd
import matplotlib.pyplot as plt
import re

df = pd.read_csv("benchmark_results.csv", decimal=',')

# Вытаскиваем scenario и размер n из строк label
def parse_label(label):
    m = re.match(r"(Comprehensive_(NoMajority|WithMajority))_n(\d+)", label)
    if m:
        return m.group(1), int(m.group(3))
    else:
        return label, None

df[['Scenario', 'Size']] = df['Label'].apply(lambda x: pd.Series(parse_label(str(x))))
df = df.dropna(subset=['Size'])  # удаляем строки без n

plt.figure(figsize=(12, 6))

for scenario in df['Scenario'].unique():
    subset = df[df['Scenario'] == scenario].sort_values('Size')
    plt.plot(subset['Size'], subset['avg_ms'], marker='o', label=scenario)

plt.xlabel("Array size (n)")
plt.ylabel("Average time (ms)")
plt.title("Boyer-Moore Majority Vote Benchmark")
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig("plots/graph.png")
plt.show()




