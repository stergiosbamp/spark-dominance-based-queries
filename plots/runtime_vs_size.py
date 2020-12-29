import matplotlib.pyplot as plt

# number of points in the data
size = [1000, 100000, 1000000]

# runtimes for task 1 (skyline)
skyline_corr = [7.21, 7.75, 9.49]
skyline_uni = [6.89, 7.41, 9.53]
skyline_norm = [6.55, 7.37, 9.43]
skyline_anti = [6.54, 7.55, 9.55]

# runtimes for task 2 (top-k dominating)
topk_corr = [12.25, 26.97, 86.79]
topk_uni = [13.28, 36.04, 99.62]
topk_norm = [12.48, 43.89, 115.31]
topk_anti = [15.74, 70.31, 384.19]

# runtimes for task 3 (top-k skyline)
topk_skyline_corr = [6.9, 8.22, 11.53]
topk_skyline_uni = [6.94, 8.99, 15.8]
topk_skyline_norm = [6.95, 11.52, 18.48]
topk_skyline_anti = [8.01, 13.02, 39.33]

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

# plot runtimes for uniform distribution
plt.figure()
plt.title("Uniform distribution")
plt.xlabel(xlabel)
plt.ylabel(ylabel)
plt.plot(size, skyline_uni)
plt.plot(size, topk_uni)
plt.plot(size, topk_skyline_uni)
plt.legend(["Skyline", "Top-k dominating", "Top-k skyline"])

# plot runtimes for normal distribution
plt.figure()
plt.title("Normal distribution")
plt.xlabel(xlabel)
plt.ylabel(ylabel)
plt.plot(size, skyline_norm)
plt.plot(size, topk_norm)
plt.plot(size, topk_skyline_norm)
plt.legend(["Skyline", "Top-k dominating", "Top-k skyline"])

# plot runtimes for anticorrelated distribution
plt.figure()
plt.title("Anticorrelated distribution")
plt.xlabel(xlabel)
plt.ylabel(ylabel)
plt.plot(size, skyline_anti)
plt.plot(size, topk_anti)
plt.plot(size, topk_skyline_anti)
plt.legend(["Skyline", "Top-k dominating", "Top-k skyline"])

plt.show()