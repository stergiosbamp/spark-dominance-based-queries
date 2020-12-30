import matplotlib.pyplot as plt

# top-k
topk = [10, 20, 40]

# runtimes for correlated (task 2 -> top-k dominating)
topk_corr = [26.97, 50.03, 134.83]

# runtimes for uniform (task 2 -> top-k dominating)
topk_uni = [36.04, 75.79, 210.62]

# runtimes for normal (task 2 -> top-k dominating)
topk_norm = [43.89, 84.38, 239.98]

# runtimes for anticorrelated (task 2 -> top-k dominating)
topk_anti = [70.31, 152.12, 451.77]

# plot options
xlabel = "Top-k"
ylabel = "Execution time [s]"

# plot runtimes for correlated distributions
plt.figure()
plt.title("Correlated distribution")
plt.xlabel(xlabel)
plt.ylabel(ylabel)
plt.plot(topk, topk_corr)
plt.legend(["Top-k dominating"])
plt.ylim([0, 460])

# plot runtimes for correlated distributions
plt.figure()
plt.title("Uniform distribution")
plt.xlabel(xlabel)
plt.ylabel(ylabel)
plt.plot(topk, topk_uni)
plt.legend(["Top-k dominating"])
plt.ylim([0, 460])

# plot runtimes for correlated distributions
plt.figure()
plt.title("Normal distribution")
plt.xlabel(xlabel)
plt.ylabel(ylabel)
plt.plot(topk, topk_norm)
plt.legend(["Top-k dominating"])
plt.ylim([0, 460])

# plot runtimes for correlated distributions
plt.figure()
plt.title("Anticorrelated distribution")
plt.xlabel(xlabel)
plt.ylabel(ylabel)
plt.plot(topk, topk_anti)
plt.legend(["Top-k dominating"])
plt.ylim([0, 460])

plt.show()