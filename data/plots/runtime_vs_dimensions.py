import matplotlib.pyplot as plt


# dimensions
dimensions = [2, 4, 8]


uni_skyline= [5.7798, 17.3254, 278.5623]  # Skyline query times
uni_top_k_dominating = [46.852, 146.318796, 2125.019312]  # Top-k dominating
uni_top_k_skyline = [11.687, 53.26, 659.45]  # Top-k skyline

norm_skyline = [5.7486, 24.3245, 298.34534]  # Skyline query times
norm_top_k_dominating = [57.057, 202.267065, 2708.12492]  # Top-k dominating
norm_top_k_skyline = [14.976, 68.54, 856.32]  # Top-k skyline


# plot options
xlabel = "Dimensions"
ylabel = "Execution time [s]"

# plot execution times for uniform
plt.figure()
plt.title("Uniform distribution")
plt.xlabel(xlabel)
plt.ylabel(ylabel)
plt.ylim(top=2800)
plt.plot(dimensions, uni_skyline)
plt.plot(dimensions, uni_top_k_dominating)
plt.plot(dimensions, uni_top_k_skyline)
plt.legend(["Skyline", "Top-k dominating", "Top-k skyline"])

# plot execution times for normal
plt.figure()
plt.title("Normal distribution")
plt.xlabel(xlabel)
plt.ylabel(ylabel)
plt.ylim(top=2800)
plt.plot(dimensions, norm_skyline)
plt.plot(dimensions, norm_top_k_dominating)
plt.plot(dimensions, norm_top_k_skyline)
plt.legend(["Skyline", "Top-k dominating", "Top-k skyline"])

plt.show()
