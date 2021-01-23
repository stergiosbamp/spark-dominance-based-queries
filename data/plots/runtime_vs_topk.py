import matplotlib.pyplot as plt

# top-k
topk = [10, 20, 40]

# runtimes for correlated (task 2 -> top-k dominating)
topk_corr = [35.061, 80.048, 188.762]

# runtimes for uniform (task 2 -> top-k dominating)
topk_uni = [46.852, 121.264, 294.868]

# runtimes for normal (task 2 -> top-k dominating)
topk_norm = [57.057, 135.008, 335.972]

# runtimes for anticorrelated (task 2 -> top-k dominating)
topk_anti = [91.403, 243.392, 632.478]

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
plt.ylim([0, 650])

# plot runtimes for correlated distributions
plt.figure()
plt.title("Uniform distribution")
plt.xlabel(xlabel)
plt.ylabel(ylabel)
plt.plot(topk, topk_uni)
plt.legend(["Top-k dominating"])
plt.ylim([0, 650])

# plot runtimes for correlated distributions
plt.figure()
plt.title("Normal distribution")
plt.xlabel(xlabel)
plt.ylabel(ylabel)
plt.plot(topk, topk_norm)
plt.legend(["Top-k dominating"])
plt.ylim([0, 650])

# plot runtimes for correlated distributions
plt.figure()
plt.title("Anticorrelated distribution")
plt.xlabel(xlabel)
plt.ylabel(ylabel)
plt.plot(topk, topk_anti)
plt.legend(["Top-k dominating"])
plt.ylim([0, 650])

plt.show()