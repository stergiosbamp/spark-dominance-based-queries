import matplotlib.pyplot as plt

# number of points in the data
size = [10000, 100000, 1000000]

# runtimes for task 1 (skyline)
skyline_corr = [2.163, 10.075, 43.654]
skyline_uni = [2.067, 5.7798, 52.6056]
skyline_norm = [1.965, 5.7486, 56.3914]
skyline_anti = [1.962, 5.889, 83.467]

# runtimes for task 2 (top-k dominating)
topk_corr = [3.675, 35.061, 512.061]
topk_uni = [3.984, 46.852, 705.3096]
topk_norm = [3.744, 57.057, 884.4277]
topk_anti = [4.722, 91.403, 3014.73893]

# runtimes for task 3 (top-k skyline)
topk_skyline_corr = [2.07, 10.686, 46.12]
topk_skyline_uni = [2.082, 11.687, 75.84]
topk_skyline_norm = [2.085, 14.976, 96.096]
topk_skyline_anti = [2.403, 16.926, 298.908]

# plot options
xlabel = "Num points"
ylabel = "Execution time [s]"

# plot runtimes for correlated distributions
plt.figure()
plt.title("Correlated distribution")
plt.xlabel(xlabel)
plt.ylabel(ylabel)
plt.plot(size, skyline_corr)
plt.plot(size, topk_corr)
plt.plot(size, topk_skyline_corr)
plt.legend(["Skyline", "Top-k dominating", "Top-k skyline"])
plt.ylim([0, 3100])

# plot runtimes for uniform distribution
plt.figure()
plt.title("Uniform distribution")
plt.xlabel(xlabel)
plt.ylabel(ylabel)
plt.plot(size, skyline_uni)
plt.plot(size, topk_uni)
plt.plot(size, topk_skyline_uni)
plt.legend(["Skyline", "Top-k dominating", "Top-k skyline"])
plt.ylim([0, 3100])

# plot runtimes for normal distribution
plt.figure()
plt.title("Normal distribution")
plt.xlabel(xlabel)
plt.ylabel(ylabel)
plt.plot(size, skyline_norm)
plt.plot(size, topk_norm)
plt.plot(size, topk_skyline_norm)
plt.legend(["Skyline", "Top-k dominating", "Top-k skyline"])
plt.ylim([0, 3100])

# plot runtimes for anticorrelated distribution
plt.figure()
plt.title("Anticorrelated distribution")
plt.xlabel(xlabel)
plt.ylabel(ylabel)
plt.plot(size, skyline_anti)
plt.plot(size, topk_anti)
plt.plot(size, topk_skyline_anti)
plt.legend(["Skyline", "Top-k dominating", "Top-k skyline"])
plt.ylim([0, 3100])

plt.show()