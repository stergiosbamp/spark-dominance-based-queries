import matplotlib.pyplot as plt

# cores
cores = [1, 2, 4]

# runtimes for correlated (task 2 -> top-k dominating)
topk_corr = [157.3, 112.77, 81.79]

#runtimes for anticorrelation (task 2 -> top-k dominating)
topk_anti = [769.51, 545.57, 384.19]

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

# plot runtimes for anticorrelated distributions
plt.figure()
plt.title("Anticorrelated distribution")
plt.xlabel(xlabel)
plt.ylabel(ylabel)
plt.plot(cores, topk_anti)
plt.legend(["Top-k dominating"])

plt.show()