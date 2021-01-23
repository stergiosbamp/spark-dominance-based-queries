import matplotlib.pyplot as plt

# cores
cores = [1, 2, 4]

# runtimes for correlated (task 2 -> top-k dominating)
topk_corr = [87.26310377, 54.1166535, 35.061]

#runtimes for anticorrelation (task 2 -> top-k dominating)
topk_anti = [226.0694232, 139.2524705, 91.403]

# plot options
xlabel = "Num cores"
ylabel = "Execution time [s]"

# plot runtimes for correlated distributions
plt.figure()
plt.title("Correlated distribution")
plt.xlabel(xlabel)
plt.ylabel(ylabel)
plt.plot(cores, topk_corr)
plt.legend(["Top-k dominating"])
plt.ylim([0, 240])

# plot runtimes for anticorrelated distributions
plt.figure()
plt.title("Anticorrelated distribution")
plt.xlabel(xlabel)
plt.ylabel(ylabel)
plt.plot(cores, topk_anti)
plt.legend(["Top-k dominating"])
plt.ylim([0, 240])

plt.show()